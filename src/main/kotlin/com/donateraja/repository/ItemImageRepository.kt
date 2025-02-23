package com.donateraja.repository

import com.donateraja.entity.item.ItemImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ItemImageRepository : JpaRepository<ItemImage, Long> {
    fun findByItemId(itemId: Long): List<ItemImage>

    // ItemImageRepository.kt
    fun deleteByItemId(itemId: Long)

    @Query("SELECT i.imageUrl FROM ItemImage i WHERE i.item.id = :itemId")
    fun findImagesByItemId(@Param("itemId") itemId: Long): List<String>
}
