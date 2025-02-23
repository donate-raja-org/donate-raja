package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.annotation.RequireAdmin
import com.donateraja.annotation.RequireUser
import com.donateraja.domain.reports.UserReportRequestDTO
import com.donateraja.domain.reports.UserReportResponseDTO
import com.donateraja.service.UserReportService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user-reports")
@Tag(name = "User Reports", description = "APIs for reporting users and managing reports")
class UserReportController(private val userReportService: UserReportService) {

    @PostMapping
    @RequireUser
    @ApiOperationWithCustomResponses(
        summary = "Report a user",
        description = "Allows a user to report another user.",
        successSchema = UserReportResponseDTO::class
    )
    fun reportUser(@RequestBody request: UserReportRequestDTO): ResponseEntity<UserReportResponseDTO> =
        ResponseEntity.ok(userReportService.reportUser(request))

    @GetMapping("/{userId}")
    @RequireAdmin
    @ApiOperationWithCustomResponses(
        summary = "Get reports for a user",
        description = "Retrieves all reports filed against a user.",
        successSchema = List::class
    )
    fun getReportsForUser(@PathVariable userId: Long): ResponseEntity<List<UserReportResponseDTO>> =
        ResponseEntity.ok(userReportService.getReportsForUser(userId))

    @PatchMapping("/{reportId}/resolve")
    @RequireAdmin
    @ApiOperationWithCustomResponses(
        summary = "Resolve a user report",
        description = "Marks a report as resolved.",
        successSchema = Unit::class
    )
    fun resolveReport(@PathVariable reportId: Long): ResponseEntity<Unit> {
        userReportService.resolveReport(reportId)
        return ResponseEntity.noContent().build()
    }
}
