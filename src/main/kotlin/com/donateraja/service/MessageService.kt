package com.donateraja.service

import com.donateraja.domain.message.MessageRequestDTO
import com.donateraja.domain.message.MessageResponseDTO

interface MessageService {
    fun sendMessage(request: MessageRequestDTO): MessageResponseDTO
    fun getUserMessages(): List<MessageResponseDTO>
    fun softDeleteMessage(messageId: Long): MessageResponseDTO
}
