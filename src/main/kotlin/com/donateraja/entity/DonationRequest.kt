package com.donateraja.entity

import com.donateraja.entity.item.Item
import com.donateraja.entity.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "donation_requests")
data class DonationRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val requestId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: Item,

    val requestDate: LocalDateTime,

    val status: String
)