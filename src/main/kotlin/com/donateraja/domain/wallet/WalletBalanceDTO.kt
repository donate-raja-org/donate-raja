package com.donateraja.domain.wallet

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class WalletBalanceDTO(@JsonProperty("balance") val balance: BigDecimal)
