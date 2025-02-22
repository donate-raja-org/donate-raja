package com.donateraja.domain.request

import com.donateraja.entity.constants.RequestStatus
import com.donateraja.entity.constants.RequestType
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

class ItemRequestCreateDTO {

    @JsonProperty("user_id")
    @NotNull(message = "User ID cannot be null")
    var userId: Long? = null

    @JsonProperty("item_id")
    @NotNull(message = "Item ID cannot be null")
    var itemId: Long? = null

    @JsonProperty("request_type")
    @NotBlank(message = "Request type cannot be blank")
    var requestType: RequestType? = RequestType.RENTAL // Values: "DONATION" or "RENTAL"

    @JsonProperty("message")
    var message: String? = null

    @JsonProperty("rental_start_date")
    var rentalStartDate: LocalDateTime? = null // Only for rental requests

    @JsonProperty("rental_end_date")
    var rentalEndDate: LocalDateTime? = null // Only for rental requests

    @JsonProperty("status")
    @NotNull(message = "Status cannot be null")
    var status: RequestStatus? = null

    constructor()

    constructor(
        userId: Long,
        itemId: Long,
        requestType: RequestType,
        message: String?,
        rentalStartDate: LocalDateTime?,
        rentalEndDate: LocalDateTime?,
        status: RequestStatus
    ) {
        this.userId = userId
        this.itemId = itemId
        this.requestType = requestType
        this.message = message
        this.rentalStartDate = rentalStartDate
        this.rentalEndDate = rentalEndDate
        this.status = status
    }
}
