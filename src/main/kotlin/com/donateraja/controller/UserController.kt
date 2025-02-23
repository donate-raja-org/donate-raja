package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.annotation.RequireUser
import com.donateraja.domain.user.ChangePasswordDto
import com.donateraja.domain.user.UserProfileDto
import com.donateraja.domain.user.UserRegistrationResponse
import com.donateraja.service.impl.UserServiceImpl
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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
@Tag(name = "User Management", description = "APIs related to User Management")
class UserController(private val userService: UserServiceImpl) {

    @GetMapping("/me")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Get current user profile",
        description = "Fetches the logged-in user's profile.",
        successSchema = UserProfileDto::class
    )
    fun getCurrentUserProfile(): ResponseEntity<UserProfileDto> {
        val userProfile = userService.getCurrentUserProfile()
        return ResponseEntity(userProfile, HttpStatus.OK)
    }

    @PutMapping("/me")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Update profile",
        description = "Updates the logged-in user's profile.",
        successSchema = UserProfileDto::class
    )
    fun updateProfile(@RequestBody userUpdateDTO: UserProfileDto): ResponseEntity<UserProfileDto> {
        val updatedProfile = userService.updateUserProfile(userUpdateDTO)
        return ResponseEntity(updatedProfile, HttpStatus.OK)
    }

    @PutMapping("/me/password")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Change password",
        description = "Allows the logged-in user to change their password."
    )
    fun changePassword(@RequestBody changePasswordDto: ChangePasswordDto): ResponseEntity<Any> {
        userService.changePassword(changePasswordDto)
        return ResponseEntity(HttpStatus.OK)
    }

    @PatchMapping("/me/avatar")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Update profile picture",
        description = "Updates the logged-in user's profile picture.",
        successSchema = UserProfileDto::class
    )
    fun updateProfilePicture(@RequestParam("file") file: MultipartFile): ResponseEntity<UserProfileDto> {
        val updatedProfile = userService.updateProfilePicture(file)
        return ResponseEntity(updatedProfile, HttpStatus.OK)
    }

    @GetMapping("/{userId}")
    @ApiOperationWithCustomResponses(
        summary = "Get public user profile",
        description = "Fetches the public profile of a user.",
        successSchema = UserProfileDto::class
    )
    fun getPublicUserProfile(@PathVariable userId: Long): ResponseEntity<UserProfileDto> {
        val userProfile = userService.getUserProfile(userId)
        return ResponseEntity(userProfile, HttpStatus.OK)
    }

    @PostMapping("/{userId}/verify-email")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Resend verification email",
        description = "Resends the email verification link to the user.",
        successSchema = UserRegistrationResponse::class
    )
    fun resendVerificationEmail(@PathVariable userId: Long): ResponseEntity<UserRegistrationResponse> {
        val response = userService.resendVerificationEmail(userId)
        return ResponseEntity(response, HttpStatus.OK)
    }
}
