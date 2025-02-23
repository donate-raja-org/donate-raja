package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.annotation.RequireUser
import com.donateraja.domain.reviews.ItemReviewRequestDTO
import com.donateraja.domain.reviews.ItemReviewResponseDTO
import com.donateraja.service.ItemReviewService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items/{itemId}/reviews")
@Tag(name = "Item Reviews", description = "APIs for reviewing and rating items")
class ItemReviewController(private val itemReviewService: ItemReviewService) {

    @PostMapping
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Add a review to an item",
        description = "Allows an authenticated user to add a review with a rating.",
        successSchema = ItemReviewResponseDTO::class
    )
    fun addReview(@PathVariable itemId: Long, @RequestBody request: ItemReviewRequestDTO): ResponseEntity<ItemReviewResponseDTO> =
        ResponseEntity.ok(itemReviewService.addReview(request))

    @GetMapping
    @ApiOperationWithCustomResponses(
        summary = "Get all reviews for an item",
        description = "Retrieves all reviews for the given item.",
        successSchema = List::class
    )
    fun getReviews(@PathVariable itemId: Long): ResponseEntity<List<ItemReviewResponseDTO>> =
        ResponseEntity.ok(itemReviewService.getReviewsForItem(itemId))

    @GetMapping("/average")
    @ApiOperationWithCustomResponses(
        summary = "Get item average rating",
        description = "Retrieves the average rating for an item.",
        successSchema = Double::class
    )
    fun getAverageRating(@PathVariable itemId: Long): ResponseEntity<Double> =
        ResponseEntity.ok(itemReviewService.getItemAverageRating(itemId))
}
