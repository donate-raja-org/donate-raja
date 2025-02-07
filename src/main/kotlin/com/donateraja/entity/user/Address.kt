package com.donateraja.entity.user

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "addresses")
@JsonIgnoreProperties("user")
class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    var user: User? = null, // Set nullable to allow default constructor

    @Column(nullable = false)
    var street: String = "",

    @Column(nullable = false)
    var city: String = "",

    @Column(nullable = false)
    var state: String = "",

    @Column(nullable = false)
    var pincode: String = "",

    var country: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    // ✅ Default constructor required by JPA
    constructor() : this(
        id = 0,
        user = null, // Null user by default
        street = "",
        city = "",
        state = "",
        pincode = "",
        country = null,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )
}
