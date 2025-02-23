package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.annotation.RequireUser
import com.donateraja.domain.wallet.WalletBalanceDTO
import com.donateraja.domain.wallet.WalletTransactionDTO
import com.donateraja.domain.wallet.WalletTransactionResponseDTO
import com.donateraja.service.WalletService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wallet")
@Tag(name = "Wallet", description = "APIs related to User Wallet and Point Transactions")
class WalletController(private val walletService: WalletService) {

    @GetMapping("/balance")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Get wallet balance",
        description = "Retrieves the wallet balance for the authenticated user.",
        successSchema = WalletBalanceDTO::class
    )
    fun getWalletBalance(@RequestAttribute("user_id") userId: Long): ResponseEntity<WalletBalanceDTO> =
        ResponseEntity.ok(walletService.getWalletBalance(userId))

    @GetMapping("/transactions")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Get wallet transaction history",
        description = "Retrieves the wallet transaction history for the authenticated user.",
        successSchema = WalletTransactionResponseDTO::class
    )
    fun getWalletTransactions(@RequestAttribute("user_id") userId: Long): ResponseEntity<List<WalletTransactionResponseDTO>> =
        ResponseEntity.ok(walletService.getWalletTransactions(userId))

    @PostMapping("/redeem")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Redeem wallet points",
        description = "Allows users to redeem their wallet balance for features.",
        successSchema = Void::class
    )
    fun redeemWalletPoints(
        @RequestAttribute("user_id") userId: Long,
        @RequestBody transactionDTO: WalletTransactionDTO
    ): ResponseEntity<Void> {
        walletService.addTransaction(userId, transactionDTO)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/transactions")
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Create wallet transaction",
        description = "Record a wallet transaction (EARN/SPEND)",
        successSchema = WalletTransactionResponseDTO::class
    )
    fun createTransaction(
        @RequestAttribute("user_id") userId: Long,
        @Valid @RequestBody transactionDTO: WalletTransactionDTO
    ): ResponseEntity<WalletTransactionResponseDTO> {
        val transaction = walletService.addTransaction(userId, transactionDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction)
    }
}
