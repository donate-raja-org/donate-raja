package com.donateraja.entity.item

import com.donateraja.entity.constants.Category
import com.donateraja.entity.constants.Condition
import com.donateraja.entity.constants.DonationOrRent
import com.donateraja.entity.constants.ItemStatus
import com.donateraja.entity.user.User
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
@Table(name = "items")
class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var itemName: String = "",

    @Column(nullable = false, length = 1000)
    var description: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var condition: Condition = Condition.NEW,

    @Column(nullable = false)
    var price: Double = 0.0,

    @Column(nullable = false)
    var location: String = "",

    @Column(nullable = false)
    var pincode: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var category: Category = Category.ELECTRONICS,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var donationOrRent: DonationOrRent = DonationOrRent.RENT,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ItemStatus = ItemStatus.AVAILABLE,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
