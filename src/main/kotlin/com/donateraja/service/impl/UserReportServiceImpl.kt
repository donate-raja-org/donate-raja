package com.donateraja.service.impl

import com.donateraja.common.exception.ServiceException
import com.donateraja.domain.reports.UserReportRequestDTO
import com.donateraja.domain.reports.UserReportResponseDTO
import com.donateraja.entity.constants.ReportStatus
import com.donateraja.entity.user.UserReport
import com.donateraja.repository.UserReportRepository
import com.donateraja.repository.UserRepository
import com.donateraja.service.UserReportService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserReportServiceImpl(private val userReportRepository: UserReportRepository, private val userRepository: UserRepository) :
    UserReportService {

    override fun reportUser(request: UserReportRequestDTO): UserReportResponseDTO {
        val reporterEmail = SecurityContextHolder.getContext().authentication.name
        val reporter = userRepository.findByEmail(reporterEmail)
            ?: throw ServiceException(HttpStatus.NOT_FOUND, "Reporting user not found")

        val reportedUser = userRepository.findById(request.reportedUserId)
            .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "User being reported not found") }

        val report = UserReport(
            reportedUser = reportedUser,
            reportedByUser = reporter,
            reason = request.reason
        )

        return UserReportResponseDTO.fromEntity(userReportRepository.save(report))
    }

    override fun getReportsForUser(userId: Long): List<UserReportResponseDTO> = userReportRepository.findReportsByUserId(userId)
        .map { UserReportResponseDTO.fromEntity(it) }

    @Transactional
    override fun resolveReport(reportId: Long) {
        val report = userReportRepository.findById(reportId)
            .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "Report not found") }

        report.status = ReportStatus.RESOLVED
        userReportRepository.save(report)
    }
}
