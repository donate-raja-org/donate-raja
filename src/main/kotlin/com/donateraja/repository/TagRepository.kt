package com.donateraja.repository

import com.donateraja.entity.item.ItemTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<ItemTag, Long> {
    fun deleteByItemId(itemId: Long)
    fun findByItemId(itemId: Long): List<ItemTag>
    fun findByTag(tag: String): ItemTag?

    @Query("SELECT t.tag FROM ItemTag t WHERE t.item.id = :itemId")
    fun findTagsByItemId(@Param("itemId") itemId: Long): List<String>
}
