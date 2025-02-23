package com.donateraja.service

import com.donateraja.domain.reports.UserReportRequestDTO
import com.donateraja.domain.reports.UserReportResponseDTO

interface UserReportService {
    fun reportUser(request: UserReportRequestDTO): UserReportResponseDTO
    fun getReportsForUser(userId: Long): List<UserReportResponseDTO>
    fun resolveReport(reportId: Long)
}
