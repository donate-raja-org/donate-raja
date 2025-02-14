package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.annotation.RequireAdmin
import com.donateraja.model.user.ChangePasswordDto
import com.donateraja.model.user.ResetPasswordDto
import com.donateraja.model.user.UserProfileDto
import com.donateraja.service.impl.UserServiceImpl
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs related to User Registration and Authentication")
class UserController(private val userService: UserServiceImpl) {

    // Get user profile by ID
    @GetMapping("/{userId}")
    @ApiOperationWithCustomResponses(
        summary = "Get user profile",
        description = "Fetches the user profile details based on user ID.",
        successSchema = UserProfileDto::class
    )
    fun getUserProfile(@PathVariable userId: Long): ResponseEntity<UserProfileDto> {
        val userProfile = userService.getUserProfile(userId)
        return ResponseEntity(userProfile, HttpStatus.OK)
    }

    // Update user profile details
    @PutMapping("/{userId}")
    @ApiOperationWithCustomResponses(
        summary = "Update user profile",
        description = "Updates the user profile information.",
        successSchema = UserProfileDto::class
    )
    fun updateUserProfile(
        @PathVariable userId: Long,
        @RequestBody userProfileDto: UserProfileDto
    ): ResponseEntity<UserProfileDto> {
        val updatedProfile = userService.updateUserProfile(userId, userProfileDto)
        return ResponseEntity(updatedProfile, HttpStatus.OK)
    }

    // Change user password
    @PutMapping("/{userId}/change-password")
    @ApiOperationWithCustomResponses(
        summary = "Change password",
        description = "Allows user to change their password."
//        successSchema = AuthResponse::class
    )
    fun changePassword(@PathVariable userId: Long, @RequestBody changePasswordDto: ChangePasswordDto): ResponseEntity<Any> {
        userService.changePassword(userId, changePasswordDto)
        return ResponseEntity(HttpStatus.OK)
    }

    // Reset user password (This functionality is not yet implemented)
    @PostMapping("/{userId}/reset-password")
    @ApiOperationWithCustomResponses(
        summary = "Reset password",
        description = "Allows user to reset their password. (Not implemented)"
//        successSchema = AuthResponse::class
    )
    fun resetPassword(@PathVariable userId: Long, @RequestBody resetPasswordDto: ResetPasswordDto): ResponseEntity<Any> {
        userService.resetPassword(userId, resetPasswordDto)
        return ResponseEntity(HttpStatus.OK)
    }

    // Add user as admin role
    @PutMapping("/{userId}/add-admin")
    @ApiOperationWithCustomResponses(
        summary = "Add admin role",
        description = "Assigns an admin role to the user."
//        successSchema = AuthResponse::class
    )
    @RequireAdmin
    fun addAdminRole(@PathVariable userId: Long): ResponseEntity<Any> {
        userService.addAdminRole(userId)
        return ResponseEntity(HttpStatus.OK)
    }

    // Endpoint for updating user profile picture
    @PutMapping("/{userId}/profile-picture")
    @ApiOperationWithCustomResponses(
        summary = "Update profile picture",
        description = "Allows user to upload a new profile picture.",
        successSchema = UserProfileDto::class
    )
    fun updateProfilePicture(
        @PathVariable userId: Long,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<UserProfileDto> {
        val updatedProfile = userService.updateProfilePicture(userId, file)
        return ResponseEntity(updatedProfile, HttpStatus.OK)
    }
}
