package com.donateraja.entity.item

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
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
