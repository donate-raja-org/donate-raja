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
@Table(name = "payment_transactions")
data class PaymentTransaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val paymentId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: Item,

    val amount: BigDecimal,

    val transactionDate: LocalDateTime
)
