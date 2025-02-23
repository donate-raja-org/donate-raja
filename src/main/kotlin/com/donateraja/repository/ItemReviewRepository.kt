package com.donateraja.repository

import com.donateraja.entity.item.ItemReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemReviewRepository : JpaRepository<ItemReview, Long> {

    @Query("SELECT r FROM ItemReview r WHERE r.item.id = :itemId ORDER BY r.createdAt DESC")
    fun findByItemId(@Param("itemId") itemId: Long): List<ItemReview>

    @Query("SELECT AVG(r.rating) FROM ItemReview r WHERE r.item.id = :itemId")
    fun findAverageRatingByItemId(@Param("itemId") itemId: Long): Double?
}
