package com.donateraja.service.impl
import com.donateraja.entity.constants.Role
import com.donateraja.entity.user.UserRole
import com.donateraja.model.user.ChangePasswordDto
import com.donateraja.model.user.ResetPasswordDto
import com.donateraja.model.user.UserProfileDto
import com.donateraja.repository.UserRepository
import com.donateraja.repository.UserRolesRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

@Service
class UserServiceImpl(private val userRolesRepository: UserRolesRepository, private val userRepository: UserRepository) {

    // Placeholder function to get user profile (to be replaced with actual implementation)
    fun getUserProfile(userId: Long): UserProfileDto {
        // Here, you would normally fetch user details from the database
        return UserProfileDto(userId, "", "", "", "", "", "")
    }

    // Placeholder function to update user profile (to be replaced with actual implementation)
    fun updateUserProfile(userId: Long, userProfileDto: UserProfileDto): UserProfileDto {
        // Here, you would normally update the user details in the database
        return UserProfileDto(userId, "", "", "", "", "", "")
    }

    // Change password functionality
    fun changePassword(userId: Long, changePasswordDto: ChangePasswordDto) {
        // Logic to change the user's password in the database
    }

    // Reset password functionality (not implemented)
    fun resetPassword(userId: Long, resetPasswordDto: ResetPasswordDto) {
        // Logic to reset the user's password
    }

    // Add admin role functionality (to be implemented)
    fun addAdminRole(userId: Long) {
        // Fetch the user by ID
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }

        // Check if the user already has an admin role
        if (userRolesRepository.existsByUserAndRole(user, Role.ADMIN)) {
            throw IllegalArgumentException("User already has admin role")
        }

        // Create the UserRole without passing the ID (ID is auto-generated)
        val userRole = UserRole(user = user, role = Role.ADMIN)

        // Save the UserRole entity
        userRolesRepository.save(userRole)
    }

    // Function to update profile picture
    fun updateProfilePicture(userId: Long, file: MultipartFile): UserProfileDto {
        // Validate file type and size if necessary (e.g., check for image file)
        if (file.isEmpty) {
            throw IllegalArgumentException("File cannot be empty")
        }

        // Generate a unique filename based on the user ID and current timestamp
        val fileName = "${userId}_${System.currentTimeMillis()}.jpg"
        val uploadDir = Paths.get("uploads/profile-pictures")

        // Ensure the directory exists
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir)
        }

        // Create file path
        val filePath = uploadDir.resolve(fileName)

        // Save the file
        file.inputStream.use { input ->
            Files.copy(input, filePath)
        }

        // Return the updated user profile with new profile picture filename
        // In a real-world scenario, you would save this information in the database
        return UserProfileDto(userId, "", "", "", "", "", "")
    }
}
