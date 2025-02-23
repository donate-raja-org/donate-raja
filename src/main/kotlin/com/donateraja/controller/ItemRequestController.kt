package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.domain.request.ItemRequestCreateDTO
import com.donateraja.domain.request.ItemRequestResponseDTO
import com.donateraja.domain.request.ItemRequestStatusDTO
import com.donateraja.service.ItemRequestService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/requests")
@Tag(name = "Item Requests", description = "APIs related to item donation/rental requests")
class ItemRequestController(private val itemRequestService: ItemRequestService) {

    @PostMapping("/items")
    @ApiOperationWithCustomResponses(
        summary = "Create item request",
        description = "Creates a new request for donation or rental.",
        successSchema = ItemRequestResponseDTO::class
    )
    fun createRequest(@RequestBody requestCreateDTO: ItemRequestCreateDTO): ResponseEntity<ItemRequestResponseDTO> =
        ResponseEntity.ok(itemRequestService.createRequest(requestCreateDTO))

    @GetMapping("/{requestId}")
    @ApiOperationWithCustomResponses(
        summary = "Get item request",
        description = "Fetches an item request by ID.",
        successSchema = ItemRequestResponseDTO::class
    )
    fun getRequest(@PathVariable requestId: Long): ResponseEntity<ItemRequestResponseDTO> =
        ResponseEntity.ok(itemRequestService.getRequestById(requestId))

    @PatchMapping("/{requestId}")
    @ApiOperationWithCustomResponses(
        summary = "Update request status",
        description = "Updates the status of an item request.",
        successSchema = ItemRequestResponseDTO::class
    )
    fun updateRequestStatus(
        @PathVariable requestId: Long,
        @RequestBody requestStatusDTO: ItemRequestStatusDTO
    ): ResponseEntity<ItemRequestResponseDTO> =
        ResponseEntity.ok(itemRequestService.updateRequestStatus(requestId, requestStatusDTO))

    @GetMapping("/me/sent")
    @ApiOperationWithCustomResponses(
        summary = "Get user's sent requests",
        description = "Retrieves all item requests sent by the user.",
        successSchema = List::class
    )
    fun getSentRequests(@RequestParam userId: Long): ResponseEntity<List<ItemRequestResponseDTO>> =
        ResponseEntity.ok(itemRequestService.getSentRequests(userId))

    @GetMapping("/me/received")
    @ApiOperationWithCustomResponses(
        summary = "Get user's received requests",
        description = "Retrieves all item requests received by the user.",
        successSchema = List::class
    )
    fun getReceivedRequests(@RequestParam userId: Long): ResponseEntity<List<ItemRequestResponseDTO>> =
        ResponseEntity.ok(itemRequestService.getReceivedRequests(userId))
}
