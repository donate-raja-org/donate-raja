package com.donateraja.service

import com.donateraja.model.user.*

interface UserService {
    fun registerUser(userRegistrationDto: UserRegistrationDto): Any
    fun loginUser(userLoginDto: UserLoginDto): Any
    fun logoutUser()
    fun getUserProfile(userId: Long): UserProfileDto
    fun updateUserProfile(userId: Long, userProfileDto: UserProfileDto): UserProfileDto
    fun changePassword(userId: Long, changePasswordDto: ChangePasswordDto)
    fun resetPassword(userId: Long, resetPasswordDto: ResetPasswordDto)
}