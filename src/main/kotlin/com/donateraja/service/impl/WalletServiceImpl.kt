package com.donateraja.service.impl

import com.donateraja.common.exception.ServiceException
import com.donateraja.domain.wallet.WalletBalanceDTO
import com.donateraja.domain.wallet.WalletTransactionDTO
import com.donateraja.domain.wallet.WalletTransactionResponseDTO
import com.donateraja.entity.constants.TransactionType
import com.donateraja.entity.wallet.Wallet
import com.donateraja.entity.wallet.WalletTransaction
import com.donateraja.repository.UserRepository
import com.donateraja.repository.WalletRepository
import com.donateraja.repository.WalletTransactionRepository
import com.donateraja.service.WalletService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class WalletServiceImpl(
    private val walletRepository: WalletRepository,
    private val walletTransactionRepository: WalletTransactionRepository,
    private val userRepository: UserRepository
) : WalletService {

    override fun getWalletBalance(userId: Long): WalletBalanceDTO {
        val wallet = walletRepository.findByUserId(userId)
            ?: throw ServiceException(HttpStatus.NOT_FOUND, "Wallet not found for user")

        return WalletBalanceDTO(wallet.balance)
    }

//    override fun getWalletTransactions(userId: Long): List<WalletTransactionResponseDTO> {
//        val user = userRepository.findById(userId)
//            .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "User not found") }
//
//        return walletTransactionRepository.findByWallet_UserOrderByCreatedAtDesc(user)
//            .map { convertToDto(it) }
//    }

    override fun getWalletTransactions(userId: Long): List<WalletTransactionResponseDTO> {
        val user = userRepository.findById(userId).orElseThrow()
        val transactions = walletTransactionRepository.findByWallet_UserOrderByCreatedAtDesc(user)
        return transactions.map {
            WalletTransactionResponseDTO(
                transactionId = it.id,
                amount = it.changeAmount,
                transactionType = it.transactionType,
                description = it.description,
                createdAt = it.createdAt,
                balanceAfter = it.changeAmount
            )
        }
    }

    @Transactional
    override fun addTransaction(userId: Long, transactionDTO: WalletTransactionDTO): WalletTransactionResponseDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "User not found") }

        val wallet = walletRepository.findByUser(user)
            ?: throw ServiceException(HttpStatus.NOT_FOUND, "Wallet not found")

        validateTransaction(transactionDTO, wallet)

        // Update wallet balance first
        wallet.apply {
            balance = when (transactionDTO.transactionType) {
                TransactionType.EARN -> balance.add(transactionDTO.amount)
                TransactionType.SPEND -> balance.subtract(transactionDTO.amount)
            }
            lastUpdated = LocalDateTime.now()
        }

        // Create and link transaction
        val transaction = WalletTransaction(
            wallet = wallet, // Correct reference to wallet instead of user
            changeAmount = transactionDTO.amount,
            transactionType = transactionDTO.transactionType,
            description = transactionDTO.description,
            balanceAfter = wallet.balance // Store final balance
        )

        // Add transaction to wallet's list (cascading save)
        wallet.transactions.add(transaction)

        // Save both entities
        val savedWallet = walletRepository.save(wallet)
        val savedTransaction = wallet.transactions.last()

        return WalletTransactionResponseDTO(
            transactionId = savedTransaction.id,
            amount = savedTransaction.changeAmount,
            transactionType = savedTransaction.transactionType,
            description = savedTransaction.description,
            createdAt = savedTransaction.createdAt,
            balanceAfter = savedTransaction.balanceAfter
        )
    }

    private fun validateTransaction(dto: WalletTransactionDTO, wallet: Wallet) {
        require(dto.amount > BigDecimal.ZERO) { "Amount must be positive" }

        if (dto.transactionType == TransactionType.SPEND && wallet.balance < dto.amount) {
            throw ServiceException(
                HttpStatus.BAD_REQUEST,
                "Insufficient balance. Available: ${wallet.balance}, Required: ${dto.amount}"
            )
        }
    }
}
