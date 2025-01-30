package com.donateraja.controller

import com.donateraja.entity.Item
import com.donateraja.service.ItemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/items")
class ItemController(private val itemService: ItemService) {

    // 1. Create a new item listing
    @PostMapping
    fun createItem(@RequestBody item: Item): ResponseEntity<Item> {
        return try {
            val createdItem = itemService.createItem(item)
            ResponseEntity(createdItem, HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    // 2. Get all item listings (with optional filters)
    @GetMapping
    fun getAllItems(
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) donationOrRent: String?,
        @RequestParam(required = false) location: String?
    ): ResponseEntity<List<Item>> {
        val items = itemService.getAllItems(category, donationOrRent, location)
        return if (items.isEmpty()) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(items, HttpStatus.OK)
        }
    }

    // 3. Get item details by ID
    @GetMapping("/{itemId}")
    fun getItemById(@PathVariable itemId: Long): ResponseEntity<Item> {
        val item = itemService.getItemById(itemId)
        return item.map { ResponseEntity(it, HttpStatus.OK) }
            .orElseGet { ResponseEntity(HttpStatus.NOT_FOUND) }
    }

    // 4. Update item listing
    @PutMapping("/{itemId}")
    fun updateItem(@PathVariable itemId: Long, @RequestBody itemDetails: Item): ResponseEntity<Item> {
        val updatedItem = itemService.updateItem(itemId, itemDetails)
        return updatedItem.map { ResponseEntity(it, HttpStatus.OK) }
            .orElseGet { ResponseEntity(HttpStatus.NOT_FOUND) }
    }

    // 5. Delete item listing
    @DeleteMapping("/{itemId}")
    fun deleteItem(@PathVariable itemId: Long): ResponseEntity<HttpStatus> {
        return try {
            val isDeleted = itemService.deleteItem(itemId)
            if (isDeleted) ResponseEntity(HttpStatus.NO_CONTENT)
            else ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    // 6. Get items by user (all items listed by a user)
    @GetMapping("/user/{userId}")
    fun getItemsByUser(@PathVariable userId: Long): ResponseEntity<List<Item>> {
        val items = itemService.getItemsByUser(userId)
        return if (items.isEmpty()) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(items, HttpStatus.OK)
        }
    }

    // 7. Search items by keywords
    // /searchItems?query=laptop&category=ELECTRONICS&donationOrRent=RENT
    @GetMapping("/search")
    fun searchItems(
        @RequestParam query: String,
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) donationOrRent: String?
    ): ResponseEntity<List<Item>> {
        val items = itemService.searchItems(query, category, donationOrRent)
        return if (items.isEmpty()) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(items, HttpStatus.OK)
        }
    }
}
