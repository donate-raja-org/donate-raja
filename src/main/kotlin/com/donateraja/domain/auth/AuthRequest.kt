package com.donateraja.domain.auth
data class AuthRequest(val emailIdOrPhoneNumber: String, val password: String)
