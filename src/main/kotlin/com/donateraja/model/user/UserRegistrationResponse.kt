package com.donateraja.model.user

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRegistrationResponse(
    @JsonProperty("message")
    val message: String,

    @JsonProperty("user_id")
    val userId: Long
)
