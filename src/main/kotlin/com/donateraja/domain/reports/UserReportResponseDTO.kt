package com.donateraja.domain.reports

import com.donateraja.entity.user.UserReport
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class UserReportResponseDTO(
    @JsonProperty("report_id") val reportId: Long,
    @JsonProperty("reported_user_id") val reportedUserId: Long,
    @JsonProperty("reported_by_user_id") val reportedByUserId: Long,
    @JsonProperty("reason") val reason: String,
    @JsonProperty("status") val status: String,
    @JsonProperty("created_at") val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(report: UserReport): UserReportResponseDTO = UserReportResponseDTO(
            reportId = report.id,
            reportedUserId = report.reportedUser.id!!,
            reportedByUserId = report.reportedByUser.id!!,
            reason = report.reason,
            status = report.status.name,
            createdAt = report.createdAt
        )
    }
}
