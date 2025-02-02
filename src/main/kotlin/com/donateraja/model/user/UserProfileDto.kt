package com.donateraja.model.user

data class UserProfileDto(
    val id: Long,
    val email: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String?
)