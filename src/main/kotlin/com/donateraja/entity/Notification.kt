package com.donateraja.entity

import com.donateraja.entity.user.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "notifications")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val notificationId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    val message: String,

    val type: String,

    val status: String,

    val createdAt: LocalDateTime
)
