package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.common.exception.ServiceException
import com.donateraja.model.user.ChangePasswordDto
import com.donateraja.model.user.ResetPasswordDto
import com.donateraja.model.user.UserLoginDto
import com.donateraja.model.user.UserProfileDto
import com.donateraja.model.user.UserRegistrationDto
import com.donateraja.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs related to User Registration and Authentication")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    @Throws(ServiceException::class)
    @ApiOperationWithCustomResponses(
        summary = "User Registration",
        description = "Registers a new user with the provided details. Returns user information upon successful " +
            "registration.",
        successSchema = UserRegistrationDto::class
    )
    fun registerUser(@RequestBody userRegistrationDto: UserRegistrationDto): ResponseEntity<Any> {
        val registeredUser = userService.registerUser(userRegistrationDto)
        return ResponseEntity(registeredUser, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    @Throws(ServiceException::class)
    fun loginUser(@RequestBody userLoginDto: UserLoginDto): ResponseEntity<Any> {
        val loginResponse = userService.loginUser(userLoginDto)
        return ResponseEntity(loginResponse, HttpStatus.OK)
    }

    @PostMapping("/logout")
    @Throws(ServiceException::class)
    fun logoutUser(): ResponseEntity<Any> {
        userService.logoutUser()
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/{userId}")
    @Throws(ServiceException::class)
    fun getUserProfile(@PathVariable userId: Long): ResponseEntity<UserProfileDto> {
        val userProfile = userService.getUserProfile(userId)
        return ResponseEntity(userProfile, HttpStatus.OK)
    }

    @PutMapping("/{userId}")
    @Throws(ServiceException::class)
    fun updateUserProfile(
        @PathVariable userId: Long,
        @RequestBody userProfileDto: UserProfileDto
    ): ResponseEntity<UserProfileDto> {
        val updatedProfile = userService.updateUserProfile(userId, userProfileDto)
        return ResponseEntity(updatedProfile, HttpStatus.OK)
    }

    @PutMapping("/{userId}/change-password")
    fun changePassword(@PathVariable userId: Long, @RequestBody changePasswordDto: ChangePasswordDto): ResponseEntity<Any> {
        userService.changePassword(userId, changePasswordDto)
        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("/{userId}/reset-password")
    fun resetPassword(@PathVariable userId: Long, @RequestBody resetPasswordDto: ResetPasswordDto): ResponseEntity<Any> {
        userService.resetPassword(userId, resetPasswordDto)
        return ResponseEntity(HttpStatus.OK)
    }
}
