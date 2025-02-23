package com.donateraja.domain.item

import com.donateraja.entity.constants.ItemStatus
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

class ItemStatusDTO {

    @JsonProperty("status")
    @NotNull(message = "status cannot be null")
    var status: ItemStatus? = null

    constructor()

    constructor(status: ItemStatus) {
        this.status = status
    }
}
