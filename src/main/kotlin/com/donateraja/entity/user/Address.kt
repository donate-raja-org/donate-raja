package com.donateraja.entity.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "addresses")
data class Address(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val addressId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    val street: String = "",

    val city: String = "",

    val state: String = "",

    val pincode: String,

    val country: String = "",

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    // Secondary constructor with user and pinCode
    constructor(user: User, pincode: String) : this(
        user = user,
        pincode = pincode,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )
}
