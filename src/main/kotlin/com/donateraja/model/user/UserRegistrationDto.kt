package com.donateraja.model.user

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
