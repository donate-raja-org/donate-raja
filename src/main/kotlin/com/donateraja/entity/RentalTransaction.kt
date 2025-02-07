package com.donateraja.entity

import com.donateraja.entity.item.Item
import com.donateraja.entity.user.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "rental_transactions")
data class RentalTransaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val transactionId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: Item,

    @ManyToOne
    @JoinColumn(name = "renter_id")
    val renter: User,

    val rentalPrice: BigDecimal,

    val rentalStartDate: LocalDateTime,

    val rentalEndDate: LocalDateTime,

    val status: String,

    val createdAt: LocalDateTime
)
