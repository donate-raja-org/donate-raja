package com.donateraja.service

import com.donateraja.domain.auth.AuthRequest
import com.donateraja.domain.auth.AuthResponse
import com.donateraja.domain.user.UserRegistrationRequest

interface AuthService {
    fun registerUser(userRegistrationRequest: UserRegistrationRequest): AuthResponse
    fun loginUser(request: AuthRequest): AuthResponse
    fun refreshToken(refreshToken: String): AuthResponse
    fun logoutUser(token: String): String
    fun verifyEmail(token: String)
    fun resendVerificationEmail(email: String)
}
