package com.donateraja.domain.request

import com.donateraja.entity.constants.RequestStatus
import com.donateraja.entity.constants.RequestType
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class ItemRequestResponseDTO {

    @JsonProperty("id")
    var id: Long = 0

    @JsonProperty("user_id")
    var userId: Long = 0

    @JsonProperty("item_id")
    var itemId: Long = 0

    @JsonProperty("request_type")
    var requestType: RequestType = RequestType.RENTAL

    @JsonProperty("message")
    var message: String = ""

    // Only for rental requests
    @JsonProperty("rental_start_date")
    var rentalStartDate: LocalDateTime? = null

    // Only for rental requests
    @JsonProperty("rental_end_date")
    var rentalEndDate: LocalDateTime? = null

    @JsonProperty("status")
    var status: RequestStatus = RequestStatus.PENDING

    @JsonProperty("created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @JsonProperty("updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()

    constructor()

    constructor(
        id: Long,
        userId: Long,
        itemId: Long,
        requestType: RequestType,
        message: String,
        rentalStartDate: LocalDateTime?,
        rentalEndDate: LocalDateTime?,
        status: RequestStatus,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime
    ) {
        this.id = id
        this.userId = userId
        this.itemId = itemId
        this.requestType = requestType
        this.message = message
        this.rentalStartDate = rentalStartDate
        this.rentalEndDate = rentalEndDate
        this.status = status
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}
