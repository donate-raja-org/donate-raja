package com.donateraja.entity.item

import com.donateraja.entity.user.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "item_reviews")
class ItemReview(
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
