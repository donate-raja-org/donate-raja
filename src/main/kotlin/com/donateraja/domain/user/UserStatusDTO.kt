package com.donateraja.domain.user

import com.donateraja.entity.constants.UserStatus
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class UserStatusDTO(
    @JsonProperty("status")
    @NotNull(message = "User status cannot be null")
    val status: UserStatus
)
