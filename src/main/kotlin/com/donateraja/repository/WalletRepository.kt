package com.donateraja.repository

import com.donateraja.entity.user.User
import com.donateraja.entity.wallet.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface WalletRepository : JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.user.id = :userId")
    fun findByUserId(@Param("userId") userId: Long): Wallet?

    fun findByUser(user: User): Wallet?
}
