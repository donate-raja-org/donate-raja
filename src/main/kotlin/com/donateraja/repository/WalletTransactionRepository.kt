package com.donateraja.repository

import com.donateraja.entity.user.User
import com.donateraja.entity.wallet.WalletTransaction
import org.springframework.data.jpa.repository.JpaRepository

interface WalletTransactionRepository : JpaRepository<WalletTransaction, Long> {
//    fun findByUserUserIdOrderByCreatedAtDesc(userId: Long): List<WalletTransaction>
//    fun findByUserOrderByCreatedAtDesc(user: User): List<WalletTransaction>
//
    @Suppress("ktlint:standard:function-naming")
    fun findByWallet_UserOrderByCreatedAtDesc(user: User): List<WalletTransaction>
}
