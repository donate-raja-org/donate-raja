package com.donateraja.domain.auth

data class LoginRequest(val username: String, val password: String)
data class AuthResponse(val token: String)