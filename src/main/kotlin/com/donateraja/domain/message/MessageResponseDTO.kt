package com.donateraja.domain.message

import com.donateraja.entity.Message
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class MessageResponseDTO(
    @JsonProperty("message_id") val messageId: Long,
    @JsonProperty("sender_id") val senderId: Long,
    @JsonProperty("receiver_id") val receiverId: Long,
    @JsonProperty("content") val content: String,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
    @JsonProperty("deleted_by_sender") val deletedBySender: Boolean,
    @JsonProperty("deleted_by_receiver") val deletedByReceiver: Boolean
) {
    companion object {
        fun fromEntity(message: Message): MessageResponseDTO = MessageResponseDTO(
            messageId = message.id,
            senderId = message.sender.id!!,
            receiverId = message.receiver.id!!,
            content = message.content,
            createdAt = message.createdAt,
            deletedBySender = message.deletedBySender,
            deletedByReceiver = message.deletedByReceiver
        )
    }
}
