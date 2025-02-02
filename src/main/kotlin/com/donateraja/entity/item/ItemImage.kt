package com.donateraja.entity.item

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "item_images")
data class ItemImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val imageId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: Item,

    val imageUrl: String,

    val createdAt: LocalDateTime
)