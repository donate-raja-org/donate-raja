package com.donateraja.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size


data class UserRegistrationDto(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    val email: String,

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String,

    @NotBlank(message = "Username is required")
    val username: String,

    @NotBlank(message = "First name is required")
    val firstName: String,

    @NotBlank(message = "Last name is required")
    val lastName: String,

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    val phoneNumber: String?
)

data class UserLoginDto(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    val email: String,

    @NotBlank(message = "Password is required")
    val password: String
)

data class UserProfileDto(
    val id: Long,
    val email: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String?
)

data class ChangePasswordDto(
    @NotBlank(message = "Current password is required")
    val currentPassword: String,

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "New password must be at least 8 characters long")
    val newPassword: String
)

data class ResetPasswordDto(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    val email: String
)