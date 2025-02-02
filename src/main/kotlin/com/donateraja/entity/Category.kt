package com.donateraja.entity

import jakarta.persistence.*

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val categoryId: Long? = null,

    val name: String,

    val description: String
)