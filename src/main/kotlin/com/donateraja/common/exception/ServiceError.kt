package com.donateraja.common.exception

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

class ServiceError {

    @JsonProperty("error_code")
    var errorCode: Int? = null

    @Schema(example = "Error message")
    var message: String? = null

    @Schema(example = "Error description")
    var description: String? = null

    @Schema(example = "transaction id to identify the request")
    @JsonProperty("transaction_id")
    var transactionId: String? = null

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    var timestamp: Instant = Instant.now()

    constructor(
        errorCode: Int?,
        message: String?,
        description: String?,
        transactionId: String?
    ) {
        this.errorCode = errorCode
        this.message = message
        this.description = description
        this.transactionId = transactionId
        this.timestamp = Instant.now()
    }

    override fun toString(): String {
        val jackson = ObjectMapper().findAndRegisterModules()
        return jackson.writerWithDefaultPrettyPrinter().writeValueAsString(this)
    }
}
