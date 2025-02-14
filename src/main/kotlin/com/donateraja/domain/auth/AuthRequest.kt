package com.donateraja.domain.auth

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class AuthRequest(
    @Schema(
        description = "User identifier, which can be an Email, Phone Number, or Customer ID",
        example = "user@example.com"
    )
    @JsonProperty("identifier")
    val identifier: String,

    @Schema(
        description = "User password",
        example = "password123"
    )
    val password: String
)
