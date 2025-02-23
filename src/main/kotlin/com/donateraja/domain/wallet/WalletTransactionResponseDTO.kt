package com.donateraja.domain.wallet

import com.donateraja.entity.constants.TransactionType
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

data class WalletTransactionResponseDTO(
    @JsonProperty("transaction_id") val transactionId: Long,
    @JsonProperty("amount") val amount: BigDecimal,
    @JsonProperty("transaction_type") val transactionType: TransactionType,
    @JsonProperty("description") val description: String?,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
    @JsonProperty("balance_after") val balanceAfter: BigDecimal?
)
