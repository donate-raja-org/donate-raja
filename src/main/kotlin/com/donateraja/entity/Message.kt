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
@Table(name = "messages")
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val messageId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "sender_id")
    val sender: User,

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    val receiver: User,

    val content: String,

    val createdAt: LocalDateTime
)
