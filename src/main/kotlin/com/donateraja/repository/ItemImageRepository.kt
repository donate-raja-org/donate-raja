package com.donateraja.repository

import com.donateraja.entity.item.ItemImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemImageRepository : JpaRepository<ItemImage, Long> {
    fun findByItemId(itemId: Long): List<ItemImage>
}