package com.donateraja.model.user

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserRegistrationDto(
    @JsonProperty("username")
    @NotBlank(message = "Username is required")
    @Size(max = 50)
    val username: String,

    @JsonProperty("email")
    @NotBlank(message = "Email is required")
    @Email
    @Size(max = 100)
    val email: String,

    @JsonProperty("phoneNumber")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    val phoneNumber: String? = null,

    @JsonProperty("password")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255)
    val password: String,

    @JsonProperty("firstName")
    @NotBlank(message = "First name is required")
    val firstName: String,

    @JsonProperty("lastName")
    @NotBlank(message = "Last name is required")
    val lastName: String,

    @JsonProperty("pincode")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid pincode format") // Assuming 6-digit format
    val pincode: String
)
