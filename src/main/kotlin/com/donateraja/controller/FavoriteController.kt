package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.annotation.RequireUser
import com.donateraja.domain.favorite.FavoriteResponseDTO
import com.donateraja.service.FavoriteService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/favorites")
@Tag(name = "Favorites", description = "APIs for managing user favorite items")
class FavoriteController(private val favoriteService: FavoriteService) {

    @PostMapping("/{itemId}")
    @ApiOperationWithCustomResponses(
        summary = "Add item to favorites",
        description = "Allows an authenticated user to mark an item as a favorite.",
        successSchema = FavoriteResponseDTO::class
    )
    @RequireUser
    fun addFavorite(@PathVariable itemId: Long): ResponseEntity<FavoriteResponseDTO> =
        ResponseEntity.ok(favoriteService.addFavorite(itemId))

    @GetMapping
    @ApiOperationWithCustomResponses(
        summary = "Get user favorites",
        description = "Retrieves all favorite items for the authenticated user.",
        successSchema = List::class
    )
    @RequireUser
    fun getUserFavorites(): ResponseEntity<List<FavoriteResponseDTO>> = ResponseEntity.ok(favoriteService.getUserFavorites())

    @DeleteMapping("/{itemId}")
    @ApiOperationWithCustomResponses(
        summary = "Remove item from favorites",
        description = "Removes an item from the user's favorites.",
        successSchema = Unit::class
    )
    @RequireUser
    fun removeFavorite(@PathVariable itemId: Long): ResponseEntity<Unit> {
        favoriteService.removeFavorite(itemId)
        return ResponseEntity.noContent().build()
    }
}
