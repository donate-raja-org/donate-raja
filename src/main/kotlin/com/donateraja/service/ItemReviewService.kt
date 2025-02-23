package com.donateraja.service

import com.donateraja.domain.reviews.ItemReviewRequestDTO
import com.donateraja.domain.reviews.ItemReviewResponseDTO

interface ItemReviewService {
    fun addReview(request: ItemReviewRequestDTO): ItemReviewResponseDTO
    fun getReviewsForItem(itemId: Long): List<ItemReviewResponseDTO>
    fun getItemAverageRating(itemId: Long): Double
}
