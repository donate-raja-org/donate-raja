package com.donateraja.domain.auth
data class AuthRequest(val emailIdOrPhoneNumberOrCustomerId: String, val password: String)
