package com.donateraja.service.impl

import com.donateraja.common.exception.ServiceException
import com.donateraja.domain.message.MessageRequestDTO
import com.donateraja.domain.message.MessageResponseDTO
import com.donateraja.entity.Message
import com.donateraja.repository.MessageRepository
import com.donateraja.repository.UserRepository
import com.donateraja.service.MessageService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MessageServiceImpl(private val messageRepository: MessageRepository, private val userRepository: UserRepository) :
    MessageService {

    override fun sendMessage(request: MessageRequestDTO): MessageResponseDTO {
        val senderUsername = SecurityContextHolder.getContext().authentication.name
        val sender = userRepository.findByEmail(senderUsername)
            ?: throw ServiceException(HttpStatus.NOT_FOUND, "Sender not found")

        val receiver = userRepository.findById(request.receiverId)
            .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "Receiver not found") }

        val message = Message(
            sender = sender,
            receiver = receiver,
            content = request.content,
            createdAt = LocalDateTime.now()
        )

        val savedMessage = messageRepository.save(message)
        return MessageResponseDTO.fromEntity(savedMessage)
    }

    override fun getUserMessages(): List<MessageResponseDTO> {
        val userUsername = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(userUsername)
            ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found")

        val messages = messageRepository.findBySenderOrReceiver(user.id!!)
        return messages.map { MessageResponseDTO.fromEntity(it) }
    }

    @Transactional
    override fun softDeleteMessage(messageId: Long): MessageResponseDTO {
        val userUsername = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(userUsername)
            ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found")

        val message = messageRepository.findById(messageId)
            .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "Message not found") }

        if (message.sender.id == user.id) {
            message.deletedBySender = true
        } else if (message.receiver.id == user.id) {
            message.deletedByReceiver = true
        } else {
            throw ServiceException(HttpStatus.FORBIDDEN, "You are not authorized to delete this message")
        }

        return MessageResponseDTO.fromEntity(messageRepository.save(message))
    }
}
