package com.donateraja.repository

import com.donateraja.entity.item.ItemTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<ItemTag, Long> {
    fun deleteByItemId(itemId: Long)
    fun findByItemId(itemId: Long): List<ItemTag>
    fun findByTag(tag: String): ItemTag?
}
