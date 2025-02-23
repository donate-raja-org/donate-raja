package com.donateraja.repository

import com.donateraja.entity.item.Favorite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface FavoriteRepository : JpaRepository<Favorite, Long> {

    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId ORDER BY f.createdAt DESC")
    fun findByUserId(@Param("userId") userId: Long): List<Favorite>

    @Query(
        "SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END FROM Favorite f WHERE f.user.id = :userId AND f.item.id = :itemId"
    )
    fun existsByUserIdAndItemId(@Param("userId") userId: Long, @Param("itemId") itemId: Long): Boolean

    fun deleteByUserIdAndItemId(userId: Long, itemId: Long)
}
