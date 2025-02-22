package com.donateraja.domain.request

import com.donateraja.entity.constants.RequestStatus
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

class ItemRequestStatusDTO {

    @JsonProperty("status")
    @NotNull(message = "Status cannot be null")
    var status: RequestStatus? = null

    constructor()

    constructor(status: RequestStatus) {
        this.status = status
    }
}
