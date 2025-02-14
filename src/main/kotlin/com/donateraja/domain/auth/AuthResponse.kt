package com.donateraja.domain.auth

data class AuthResponse(
    val token: String? = null,
    val expiresIn: Long? = null,
    val roles: List<String>? = null,
    val claims: Map<String, Any>? = null,
    val userId: String? = null
)
