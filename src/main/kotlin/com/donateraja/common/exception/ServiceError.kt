package com.donateraja.common.exception

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.time.ZoneOffset

class ServiceError {

    @JsonProperty("errorCode")
    var errorCode: Int? = null

    @Schema(example="Error message")
    var message: String? = null

    @Schema(example="Error description")
    var description: String? = null

    @Schema(example="transaction id to identify the request")
    @JsonProperty("transactionId")
    var transactionId: String? = null

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]'Z'")
    var timestampInMillis: LocalDateTime? = null

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
        this.timestampInMillis = LocalDateTime.now(ZoneOffset.UTC)
    }


    // Override the toString method using Jackson's ObjectMapper to pretty print
    override fun toString(): String {
        val jackson = ObjectMapper().findAndRegisterModules()
        return jackson.writerWithDefaultPrettyPrinter().writeValueAsString(this)
    }
}
