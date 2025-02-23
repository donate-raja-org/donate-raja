package com.donateraja.service.impl

import com.donateraja.common.exception.ServiceException
import com.donateraja.domain.reviews.ItemReviewRequestDTO
import com.donateraja.domain.reviews.ItemReviewResponseDTO
import com.donateraja.entity.item.ItemReview
import com.donateraja.repository.ItemRepository
import com.donateraja.repository.ItemReviewRepository
import com.donateraja.repository.UserRepository
import com.donateraja.service.ItemReviewService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemReviewServiceImpl(
    private val itemReviewRepository: ItemReviewRepository,
    private val itemRepository: ItemRepository,
    private val userRepository: UserRepository
) : ItemReviewService {

    @Transactional
    override fun addReview(request: ItemReviewRequestDTO): ItemReviewResponseDTO {
        val userEmail = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(userEmail)
            ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found")

        val item = itemRepository.findById(request.itemId)
            .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "Item not found") }

        val review = ItemReview(
            item = item,
            user = user,
            rating = request.rating,
            comment = request.comment
        )

        val savedReview = itemReviewRepository.save(review)
        return ItemReviewResponseDTO.fromEntity(savedReview)
    }

    override fun getReviewsForItem(itemId: Long): List<ItemReviewResponseDTO> {
        val reviews = itemReviewRepository.findByItemId(itemId)
        return reviews.map { ItemReviewResponseDTO.fromEntity(it) }
    }

    override fun getItemAverageRating(itemId: Long): Double = itemReviewRepository.findAverageRatingByItemId(itemId) ?: 0.0
}
