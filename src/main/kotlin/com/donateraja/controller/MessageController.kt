package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.annotation.RequireUser
import com.donateraja.domain.message.MessageRequestDTO
import com.donateraja.domain.message.MessageResponseDTO
import com.donateraja.service.MessageService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/messages")
@Tag(name = "Messages", description = "APIs for user-to-user messaging")
class MessageController(private val messageService: MessageService) {

    @PostMapping
    @ApiOperationWithCustomResponses(
        summary = "Send a message",
        description = "Allows an authenticated user to send a message to another user.",
        successSchema = MessageResponseDTO::class
    )
    @RequireUser
    fun sendMessage(@RequestBody request: MessageRequestDTO): ResponseEntity<MessageResponseDTO> =
        ResponseEntity.ok(messageService.sendMessage(request))

    @GetMapping
    @ApiOperationWithCustomResponses(
        summary = "Get user messages",
        description = "Retrieves all messages for the authenticated user.",
        successSchema = List::class
    )
    @RequireUser
    fun getUserMessages(): ResponseEntity<List<MessageResponseDTO>> = ResponseEntity.ok(messageService.getUserMessages())

    @PatchMapping("/{messageId}/delete")
    @ApiOperationWithCustomResponses(
        summary = "Soft delete a message",
        description = "Marks a message as deleted for either sender or receiver.",
        successSchema = MessageResponseDTO::class
    )
    @RequireUser
    fun deleteMessage(@PathVariable messageId: Long): ResponseEntity<MessageResponseDTO> =
        ResponseEntity.ok(messageService.softDeleteMessage(messageId))
}
