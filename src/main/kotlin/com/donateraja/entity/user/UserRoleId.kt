package com.donateraja.entity.user

data class UserRoleId(
    val user: Long = 0,  // Should match User's ID type
    val role: String = ""
)