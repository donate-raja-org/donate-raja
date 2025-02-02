package com.donateraja.entity

import jakarta.persistence.*

@Entity
@Table(name = "conditions")
data class Condition(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val conditionId: Long? = null,

    val name: String,

    val description: String
)