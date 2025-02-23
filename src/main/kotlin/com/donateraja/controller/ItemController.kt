package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.annotation.RequireUser
import com.donateraja.domain.admin.ItemResponseDTO
import com.donateraja.domain.item.ItemCreateDTO
import com.donateraja.domain.item.ItemStatusDTO
import com.donateraja.domain.item.ItemUpdateDTO
import com.donateraja.entity.constants.Category
import com.donateraja.entity.constants.DonationOrRent
import com.donateraja.service.ItemService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items")
@Tag(name = "Item Management", description = "Manage items for donation or rental")
class ItemController(private val itemService: ItemService) {

    @PostMapping
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Post new item",
        description = "Allows users to post an item for donation or rental.",
        successSchema = ItemResponseDTO::class
    )
    fun postItem(@RequestBody itemCreateDTO: ItemCreateDTO): ResponseEntity<ItemResponseDTO> =
        ResponseEntity.ok(itemService.postItem(itemCreateDTO))

    @GetMapping("/{itemId}")
    @ApiOperationWithCustomResponses(
        summary = "Get item details",
        description = "Retrieves details of a specific item.",
        successSchema = ItemResponseDTO::class
    )
    fun getItem(@PathVariable itemId: Long): ResponseEntity<ItemResponseDTO> = ResponseEntity.ok(itemService.getItem(itemId))

    @PutMapping("/{itemId}")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Update item",
        description = "Allows users to update an existing item.",
        successSchema = ItemResponseDTO::class
    )
    fun updateItem(@PathVariable itemId: Long, @RequestBody itemUpdateDTO: ItemUpdateDTO): ResponseEntity<ItemResponseDTO> =
        ResponseEntity.ok(itemService.updateItem(itemId, itemUpdateDTO))

    @DeleteMapping("/{itemId}")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Delete item",
        description = "Removes an item from the platform.",
        successSchema = Unit::class
    )
    fun deleteItem(@PathVariable itemId: Long): ResponseEntity<Void> {
        itemService.deleteItem(itemId)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{itemId}/status")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Update item status",
        description = "Updates the status of an existing item.",
        successSchema = ItemResponseDTO::class
    )
    fun updateItemStatus(@PathVariable itemId: Long, @RequestBody itemStatusDTO: ItemStatusDTO): ResponseEntity<ItemResponseDTO> =
        ResponseEntity.ok(itemService.updateItemStatus(itemId, itemStatusDTO))

    @GetMapping("/search")
    @ApiOperationWithCustomResponses(
        summary = "Search items by pincode",
        description = "Finds items based on location, category, and donation/rental type.",
        successSchema = Array<ItemResponseDTO>::class
    )
    fun searchItemsByPincode(
        @RequestParam pincode: String,
        @RequestParam(required = false) category: Category?,
        @RequestParam(required = false) donationOrRent: DonationOrRent?
    ): ResponseEntity<List<ItemResponseDTO>> =
        ResponseEntity.ok(itemService.searchItemsByPincode(pincode, category, donationOrRent))
}
