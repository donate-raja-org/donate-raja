package com.donateraja.entity

import com.donateraja.entity.constants.RequestStatus
import com.donateraja.entity.constants.RequestType
import com.donateraja.entity.item.Item
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "item_requests")
class ItemRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", nullable = false, updatable = false)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    val item: Item,

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type", nullable = false)
    val requestType: RequestType,

    @Column
    val message: String? = null,

    @Column(name = "rental_start_date")
    val rentalStartDate: LocalDateTime? = null,

    @Column(name = "rental_end_date")
    val rentalEndDate: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: RequestStatus = RequestStatus.PENDING,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
