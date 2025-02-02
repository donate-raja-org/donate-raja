package com.donateraja.entity.item

import com.donateraja.entity.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "item_reviews")
data class ItemReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val reviewId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: Item,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    val rating: Int,

    val comment: String,

    val createdAt: LocalDateTime
)