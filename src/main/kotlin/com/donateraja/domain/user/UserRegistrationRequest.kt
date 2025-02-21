package com.donateraja.domain.user

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class UserRegistrationRequest {

    @JsonProperty("username")
    @NotBlank(message = "username cannot be null or blank")
    @Size(max = 50)
    var username: String? = null

    @JsonProperty("email")
    @NotBlank(message = "Email cannot be null or blank")
    @Email
    @Size(max = 100)
    var email: String? = null

    @JsonProperty("phone_number")
    @NotBlank(message = "phone_number cannot be null or blank")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    var phoneNumber: String? = null

    @JsonProperty("password")
    @NotBlank(message = "password  cannot be null or blank")
    @Size(min = 6, max = 255, message = "password must be min 8 character long")
    var password: String? = null

    @JsonProperty("first_name")
    @NotBlank(message = "first_name  cannot be null or blank")
    var firstName: String? = null

    @JsonProperty("last_name")
    @NotBlank(message = "last_name cannot be null or blank")
    var lastName: String = ""

    @JsonProperty("pincode")
    @NotBlank(message = "pincode cannot be null or blank")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid pincode format")
    var pincode: String? = null

    constructor()

    constructor(
        username: String,
        email: String,
        phoneNumber: String?,
        password: String,
        firstName: String,
        lastName: String,
        pincode: String
    ) {
        this.username = username
        this.email = email
        this.phoneNumber = phoneNumber
        this.password = password
        this.firstName = firstName
        this.lastName = lastName
        this.pincode = pincode
    }
}
