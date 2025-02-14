package com.donateraja.service

import com.donateraja.model.user.ChangePasswordDto
import com.donateraja.model.user.ResetPasswordDto
import com.donateraja.model.user.UserProfileDto
import org.springframework.web.multipart.MultipartFile

interface UserService {
    fun getUserProfile(userId: Long): UserProfileDto
    fun updateUserProfile(userId: Long, userProfileDto: UserProfileDto): UserProfileDto
    fun changePassword(userId: Long, changePasswordDto: ChangePasswordDto)
    fun resetPassword(userId: Long, resetPasswordDto: ResetPasswordDto)
    fun updateProfilePicture(userId: Long, file: MultipartFile): UserProfileDto
    fun addAdminRole(userId: Long): String
}
