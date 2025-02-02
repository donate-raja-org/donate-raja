package com.donateraja.entity

import com.donateraja.entity.user.User
import jakarta.persistence.*
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