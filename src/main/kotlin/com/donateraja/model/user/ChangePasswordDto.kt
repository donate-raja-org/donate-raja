package com.donateraja.model.user

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ChangePasswordDto(
    @NotBlank(message = "Current password is required")
    val currentPassword: String,

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "New password must be at least 8 characters long")
    val newPassword: String
)
