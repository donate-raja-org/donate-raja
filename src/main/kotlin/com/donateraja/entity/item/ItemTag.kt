package com.donateraja.entity.item

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "item_tags")
data class ItemTag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val tagId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: Item,

    val tag: String,

    val createdAt: LocalDateTime
)