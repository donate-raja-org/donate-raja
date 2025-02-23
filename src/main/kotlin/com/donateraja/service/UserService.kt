package com.donateraja.service

import com.donateraja.domain.user.ChangePasswordDto
import com.donateraja.domain.user.UserProfileDto
import com.donateraja.domain.user.UserRegistrationResponse
import org.springframework.web.multipart.MultipartFile

interface UserService {
    fun getCurrentUserProfile(): UserProfileDto
    fun updateUserProfile(userUpdateDTO: UserProfileDto): UserProfileDto
    fun changePassword(changePasswordDto: ChangePasswordDto)
    fun updateProfilePicture(file: MultipartFile): UserProfileDto
    fun getUserProfile(userId: Long): UserProfileDto
    fun resendVerificationEmail(userId: Long): UserRegistrationResponse
}
