package com.donateraja.entity.wallet

import com.donateraja.entity.constants.TransactionType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "wallet_transactions")
class WalletTransaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false, updatable = false)
    var id: Long = 0,

    // Changed to reference Wallet instead of User
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    var wallet: Wallet,

    @Column(nullable = false, precision = 10, scale = 2)
    var changeAmount: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var transactionType: TransactionType,

    @Column(nullable = true)
    var description: String? = null,

    @Column(name = "balance_after", nullable = false, precision = 10, scale = 2)
    val balanceAfter: BigDecimal,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
)
