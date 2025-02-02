package com.donateraja.entity.item

import com.donateraja.entity.user.User
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "item_history")
data class ItemHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    val title: String,

    val description: String,

    val condition: String,

    val category: String,

    val imageUrl: String? = null,

    val itemType: String,

    val pricePerDay: BigDecimal,

    val availableFrom: LocalDateTime,

    val availableTo: LocalDateTime,

    val status: String,

    val createdAt: LocalDateTime,

    val updatedAt: LocalDateTime
)