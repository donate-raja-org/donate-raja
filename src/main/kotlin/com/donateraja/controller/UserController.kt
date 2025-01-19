package com.donateraja.controller

import com.donateraja.model.*
import com.donateraja.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userRegistrationDto: UserRegistrationDto): ResponseEntity<Any> {
        val registeredUser = userService.registerUser(userRegistrationDto)
        return ResponseEntity(registeredUser, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody userLoginDto: UserLoginDto): ResponseEntity<Any> {
        val loginResponse = userService.loginUser(userLoginDto)
        return ResponseEntity(loginResponse, HttpStatus.OK)
    }

    @PostMapping("/logout")
    fun logoutUser(): ResponseEntity<Any> {
        userService.logoutUser()
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/{userId}")
    fun getUserProfile(@PathVariable userId: Long): ResponseEntity<UserProfileDto> {
        val userProfile = userService.getUserProfile(userId)
        return ResponseEntity(userProfile, HttpStatus.OK)
    }

    @PutMapping("/{userId}")
    fun updateUserProfile(@PathVariable userId: Long, @RequestBody userProfileDto: UserProfileDto): ResponseEntity<UserProfileDto> {
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