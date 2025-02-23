package com.donateraja.domain.admin

import com.donateraja.entity.constants.ItemStatus
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class ItemApprovalDTO(
    @JsonProperty("status")
    @NotNull(message = "Item status cannot be null")
    val status: ItemStatus
)
