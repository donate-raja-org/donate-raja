package com.donateraja.domain.message

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageRequestDTO(@JsonProperty("receiver_id") val receiverId: Long, @JsonProperty("content") val content: String)
