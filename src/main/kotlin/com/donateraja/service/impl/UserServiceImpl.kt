package com.donateraja.service.impl

import com.donateraja.common.exception.ServiceException
import com.donateraja.domain.user.AddressResponseDTO
import com.donateraja.entity.user.Address
import com.donateraja.entity.user.User
import com.donateraja.model.user.ChangePasswordDto
import com.donateraja.model.user.UserProfileDto
import com.donateraja.model.user.UserRegistrationResponse
import com.donateraja.repository.AddressRepository
import com.donateraja.repository.UserRepository
import com.donateraja.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val addressRepository: AddressRepository
) : UserService {

    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun getCurrentUserProfile(): UserProfileDto {
        val user = getCurrentUser()
        val address = addressRepository.findByUserId(user.id) // ✅ Fetch address
        return mapToUserProfileDto(user, address)
    }

    override fun updateUserProfile(userUpdateDTO: UserProfileDto): UserProfileDto {
        val user = getCurrentUser()

        // ✅ Update user details
        user.firstName = userUpdateDTO.firstName
        user.lastName = userUpdateDTO.lastName
        user.phoneNumber = userUpdateDTO.phoneNumber
        userRepository.save(user)

        // ✅ Fetch updated address before mapping response
        val address = addressRepository.findByUserId(user.id)

        return mapToUserProfileDto(user, address)
    }

    override fun changePassword(changePasswordDto: ChangePasswordDto) {
        val user = getCurrentUser()
        if (!passwordEncoder.matches(changePasswordDto.currentPassword, user.password)) {
            throw ServiceException(HttpStatus.BAD_REQUEST, "Current password is incorrect")
        }
        user.updatePassword(passwordEncoder.encode(changePasswordDto.newPassword))
        userRepository.save(user)
    }

    override fun updateProfilePicture(file: MultipartFile): UserProfileDto {
        val user = getCurrentUser()
        user.profilePicture = "/uploads/${UUID.randomUUID()}_${file.originalFilename}"
        userRepository.save(user)

        // ✅ Fetch updated address before returning profile
        val address = addressRepository.findByUserId(user.id)

        return mapToUserProfileDto(user, address)
    }

    override fun getUserProfile(userId: Long): UserProfileDto {
        val user = userRepository.findById(userId).orElseThrow {
            throw ServiceException(HttpStatus.NOT_FOUND, "User with ID $userId not found")
        }
        val address = addressRepository.findByUserId(user.id) // ✅ Fetch address
        return mapToUserProfileDto(user, address)
    }

    override fun resendVerificationEmail(userId: Long): UserRegistrationResponse {
        val user = userRepository.findById(userId).orElseThrow {
            throw ServiceException(HttpStatus.NOT_FOUND, "User not found")
        }
        user.verificationCode = UUID.randomUUID().toString()
        userRepository.save(user)
        logger.info("Resent verification email to ${user.email}")
        return UserRegistrationResponse("Verification email sent", user.id)
    }

    private fun getCurrentUser(): User =
        userRepository.findByEmail("test@example.com") ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found")

    private fun mapToUserProfileDto(user: User, address: Address?) = UserProfileDto(
        firstName = user.firstName ?: "",
        lastName = user.lastName ?: "",
        phoneNumber = user.phoneNumber,
        profilePicture = user.profilePicture,
        userBio = user.userBio ?: "",
        gender = user.gender,
        dob = user.dob,
        address = address?.let {
            AddressResponseDTO(
                street = it.street,
                city = it.city,
                state = it.state,
                pincode = it.pincode,
                country = it.country ?: ""
            )
        }
    )
}
