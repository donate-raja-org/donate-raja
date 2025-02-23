package com.donateraja.domain.reviews

import com.donateraja.entity.item.ItemReview
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ItemReviewResponseDTO(
    @JsonProperty("review_id") val reviewId: Long,
    @JsonProperty("item_id") val itemId: Long,
    @JsonProperty("user_id") val userId: Long,
    @JsonProperty("rating") val rating: Int,
    @JsonProperty("comment") val comment: String?,
    @JsonProperty("created_at") val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(review: ItemReview): ItemReviewResponseDTO = ItemReviewResponseDTO(
            reviewId = review.id,
            itemId = review.item.id!!,
            userId = review.user.id!!,
            rating = review.rating,
            comment = review.comment,
            createdAt = review.createdAt
        )
    }
}
