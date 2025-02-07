package com.donateraja.entity.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_reports")
data class UserReport(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val reportId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "reported_user_id")
    val reportedUser: User,

    @ManyToOne
    @JoinColumn(name = "reported_by_user_id")
    val reportedByUser: User,

    val reason: String,

    val status: String,

    val createdAt: LocalDateTime
)
