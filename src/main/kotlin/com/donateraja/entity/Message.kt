package com.donateraja.entity

import com.donateraja.entity.user.User
import jakarta.persistence.Column
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
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    var sender: User,

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    var receiver: User,

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "deleted_by_sender", nullable = false)
    var deletedBySender: Boolean = false,

    @Column(name = "deleted_by_receiver", nullable = false)
    var deletedByReceiver: Boolean = false
)
