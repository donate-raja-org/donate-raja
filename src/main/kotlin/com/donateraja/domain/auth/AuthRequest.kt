package com.donateraja.domain.auth

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

class AuthRequest {
    @Schema(
        description = "User identifier, which can be an Email, Phone Number, or Customer ID",
        example = "user@example.com"
    )
    @JsonProperty("identifier")
    @NotBlank(message = "Identifier cannot be null or blank")
    var identifier: String? = null

    @Schema(
        description = "User password",
        example = "password123"
    )
    @JsonProperty("password")
    @NotBlank(message = "Password cannot be null or blank")
    var password: String? = null

    constructor(identifier: String, password: String) {
        this.identifier = identifier
        this.password = password
    }

    constructor()
    override fun toString(): String = "AuthRequest(identifier='$identifier', password='****')"
}
