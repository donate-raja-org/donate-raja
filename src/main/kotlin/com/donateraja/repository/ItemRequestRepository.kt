package com.donateraja.repository

import com.donateraja.entity.ItemRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRequestRepository : JpaRepository<ItemRequest, Long> {
    fun findByUserId(userId: Long): List<ItemRequest>
    fun findByItemId(itemId: Long): List<ItemRequest>
    fun findByItem_UserId(userId: Long): List<ItemRequest>
}
