package com.donateraja.service

import com.donateraja.model.user.ChangePasswordDto
import com.donateraja.model.user.ResetPasswordDto
import com.donateraja.model.user.UserLoginDto
import com.donateraja.model.user.UserProfileDto
import com.donateraja.model.user.UserRegistrationDto

interface UserService {
    fun registerUser(userRegistrationDto: UserRegistrationDto): Any
    fun loginUser(userLoginDto: UserLoginDto): Any
    fun logoutUser()
    fun getUserProfile(userId: Long): UserProfileDto
    fun updateUserProfile(userId: Long, userProfileDto: UserProfileDto): UserProfileDto
    fun changePassword(userId: Long, changePasswordDto: ChangePasswordDto)
    fun resetPassword(userId: Long, resetPasswordDto: ResetPasswordDto)
}
