package com.donateraja.service.impl

import com.donateraja.entity.user.User
import com.donateraja.model.user.ChangePasswordDto
import com.donateraja.model.user.ResetPasswordDto
import com.donateraja.model.user.UserLoginDto
import com.donateraja.model.user.UserProfileDto
import com.donateraja.model.user.UserRegistrationDto
import com.donateraja.repository.UserRepository
import com.donateraja.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) :
    UserService {

    @Transactional
    override fun registerUser(userRegistrationDto: UserRegistrationDto): Any {
        // Check if user already exists
        if (userRepository.existsByEmail(userRegistrationDto.email)) {
            throw IllegalArgumentException("User with this email already exists")
        }
// [select u1_0.id from users u1_0 where u1_0.email=? fetch first ? rows only] [ERROR: column u1_0.id does not exist
//  Position: 8] [n/a]
        // Create new user
        val user = User(
            email = userRegistrationDto.email,
            password = passwordEncoder.encode(userRegistrationDto.password),
            username = userRegistrationDto.username,
            firstName = userRegistrationDto.firstName,
            lastName = userRegistrationDto.lastName,
            phoneNumber = userRegistrationDto.phoneNumber
        )

        val savedUser = userRepository.save(user)

        // Return user details (excluding sensitive information)
        return UserProfileDto(
            id = savedUser.id,
            email = savedUser.email,
            username = savedUser.username,
            firstName = savedUser.firstName ?: "",
            lastName = savedUser.lastName ?: "",
            phoneNumber = savedUser.phoneNumber ?: ""
        )
    }

    override fun loginUser(userLoginDto: UserLoginDto): Any {
        val user = userRepository.findByEmail(userLoginDto.email)
            ?: throw IllegalArgumentException("User not found")

        if (!passwordEncoder.matches(userLoginDto.password, user.password)) {
            throw IllegalArgumentException("Invalid password")
        }

        // Here you would typically generate and return a JWT token
        // For simplicity, we're just returning a success message
        return mapOf("message" to "Login successful")
    }

    override fun logoutUser() {
        // Implement logout logic (e.g., invalidate JWT token)
    }

    override fun getUserProfile(userId: Long): UserProfileDto {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
        return UserProfileDto(
            id = user.id,
            email = user.email,
            username = user.username,
            firstName = user.firstName ?: "",
            lastName = user.lastName ?: "",
            phoneNumber = user.phoneNumber
        )
    }

    @Transactional
    override fun updateUserProfile(userId: Long, userProfileDto: UserProfileDto): UserProfileDto {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }

        user.apply {
            firstName = userProfileDto.firstName
            lastName = userProfileDto.lastName
            phoneNumber = userProfileDto.phoneNumber
        }

        val updatedUser = userRepository.save(user)
        return UserProfileDto(
            id = updatedUser.id,
            email = updatedUser.email,
            username = updatedUser.username,
            firstName = updatedUser.firstName ?: "",
            lastName = updatedUser.lastName ?: "",
            phoneNumber = updatedUser.phoneNumber
        )
    }

    @Transactional
    override fun changePassword(userId: Long, changePasswordDto: ChangePasswordDto) {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }

        if (!passwordEncoder.matches(changePasswordDto.currentPassword, user.password)) {
            throw IllegalArgumentException("Current password is incorrect")
        }

//        user.password = passwordEncoder.encode(changePasswordDto.newPassword)
        userRepository.save(user)
    }

    override fun resetPassword(userId: Long, resetPasswordDto: ResetPasswordDto) {
        // Implement password reset logic
        // This might involve sending a reset link to the user's email
        // For now, we'll just throw a "Not Implemented" exception
        throw NotImplementedError("Password reset functionality not implemented")
    }
}
