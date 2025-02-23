package com.donateraja.domain.favorite

import com.donateraja.entity.item.Favorite
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class FavoriteResponseDTO(
    @JsonProperty("favorite_id") val favoriteId: Long,
    @JsonProperty("user_id") val userId: Long,
    @JsonProperty("item_id") val itemId: Long,
    @JsonProperty("created_at") val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(favorite: Favorite): FavoriteResponseDTO = FavoriteResponseDTO(
            favoriteId = favorite.id,
            userId = favorite.user.id!!,
            itemId = favorite.item.id!!,
            createdAt = favorite.createdAt
        )
    }
}
