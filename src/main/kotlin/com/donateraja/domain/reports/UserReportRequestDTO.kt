package com.donateraja.domain.reports

import com.fasterxml.jackson.annotation.JsonProperty

data class UserReportRequestDTO(
    @JsonProperty("reported_user_id") val reportedUserId: Long,
    @JsonProperty("reason") val reason: String
)
