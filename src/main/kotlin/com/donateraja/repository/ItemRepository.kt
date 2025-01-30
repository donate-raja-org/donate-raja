package com.donateraja.repository

import com.donateraja.entity.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long> {



    @Query("""
        SELECT i FROM Item i
        WHERE (LOWER(i.itemName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(i.description) LIKE LOWER(CONCAT('%', :query, '%')))
        AND (:category IS NULL OR i.category = :category)
        AND (:donationOrRent IS NULL OR i.donationOrRent = :donationOrRent)
    """)
    fun findByFilters(
        @Param("query") query: String?,
        @Param("category") category: String?,
        @Param("donationOrRent") donationOrRent: String?
    ): List<Item>

    fun findByUserId(userId: Long): List<Item>

    @Query("""
        SELECT i FROM Item i
        WHERE (LOWER(i.itemName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(i.description) LIKE LOWER(CONCAT('%', :query, '%')))
        AND (:category IS NULL OR i.category = :category)
        AND (:donationOrRent IS NULL OR i.donationOrRent = :donationOrRent)
    """)
    fun findItems(
        @Param("query") query: String,
        @Param("category") category: String?,
        @Param("donationOrRent") donationOrRent: String?
    ): List<Item>
}
