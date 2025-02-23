package com.donateraja.repository

import com.donateraja.entity.constants.ReportStatus
import com.donateraja.entity.user.UserReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserReportRepository : JpaRepository<UserReport, Long> {

    @Query("SELECT r FROM UserReport r WHERE r.reportedUser.id = :userId")
    fun findReportsByUserId(@Param("userId") userId: Long): List<UserReport>

    @Query("SELECT r FROM UserReport r WHERE r.status = :status")
    fun findByStatus(@Param("status") status: ReportStatus): List<UserReport>
}
