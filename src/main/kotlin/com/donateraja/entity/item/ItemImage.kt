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
@Table(name = "item_images")
class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val imageId: Long? = null

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: Item = Item()

    val imageUrl: String = ""

    val createdAt: LocalDateTime = LocalDateTime.now()

    constructor()
    // All-argument constructor (explicitly defined)
}
