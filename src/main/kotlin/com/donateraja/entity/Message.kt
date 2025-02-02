package com.donateraja.entity

import com.donateraja.entity.user.User
import jakarta.persistence.*
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