package com.donateraja.model.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class ResetPasswordDto(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    val email: String
)
