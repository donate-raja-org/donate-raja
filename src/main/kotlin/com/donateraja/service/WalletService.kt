package com.donateraja.service

import com.donateraja.domain.wallet.WalletBalanceDTO
import com.donateraja.domain.wallet.WalletTransactionDTO
import com.donateraja.domain.wallet.WalletTransactionResponseDTO

interface WalletService {
    fun getWalletBalance(userId: Long): WalletBalanceDTO
    fun getWalletTransactions(userId: Long): List<WalletTransactionResponseDTO>
    fun addTransaction(userId: Long, transactionDTO: WalletTransactionDTO): WalletTransactionResponseDTO
}
