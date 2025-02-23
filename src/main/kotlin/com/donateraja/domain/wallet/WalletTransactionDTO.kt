package com.donateraja.domain.wallet

import com.donateraja.entity.constants.TransactionType
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class WalletTransactionDTO(
    @JsonProperty("amount") val amount: BigDecimal,
    @JsonProperty("transaction_type") val transactionType: TransactionType,
    @JsonProperty("description") val description: String?
)
