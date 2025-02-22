package com.donateraja.service.impl

import com.donateraja.common.exception.ServiceException
import com.donateraja.entity.user.User
import com.donateraja.model.user.ChangePasswordDto
import com.donateraja.model.user.UserProfileDto
import com.donateraja.model.user.UserRegistrationResponse
import com.donateraja.repository.UserRepository
import com.donateraja.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.Instant
import java.util.UUID

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) : UserService {

    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun getCurrentUserProfile(): UserProfileDto {
        val user = getCurrentUser()
        return mapToUserProfileDto(user)
    }

    override fun updateUserProfile(userUpdateDTO: UserProfileDto): UserProfileDto {
        val user = getCurrentUser()
        user.firstName = userUpdateDTO.firstName
        user.lastName = userUpdateDTO.lastName
        user.phoneNumber = userUpdateDTO.phoneNumber
        userRepository.save(user)
        return mapToUserProfileDto(user)
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
        return mapToUserProfileDto(user)
    }

    override fun getUserProfile(userId: Long): UserProfileDto {
        val user = userRepository.findById(userId).orElseThrow {
            throw ServiceException(HttpStatus.NOT_FOUND, "User with ID $userId not found")
        }
        return mapToUserProfileDto(user)
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
    private fun mapToUserProfileDto(user: User) = UserProfileDto(
        firstName = user.firstName ?: "",
        lastName = user.lastName ?: "",
        phoneNumber = user.phoneNumber,
        profilePicture = user.profilePicture,
        userBio = user.userBio ?: "",
        gender = user.gender,
        dob = user.dob ?: Instant.now()
    )
}
