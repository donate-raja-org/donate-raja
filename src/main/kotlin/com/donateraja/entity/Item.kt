package com.donateraja.entity

import com.donateraja.entity.constants.Category
import com.donateraja.entity.constants.Condition
import com.donateraja.entity.constants.DonationOrRent
import jakarta.persistence.*

@Entity
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var itemName: String,

    @Column(nullable = false)
    var description: String,

    @Enumerated(EnumType.STRING)
    var condition: Condition, // e.g. NEW, USED, etc.

    var price: Double,

    var location: String,

    @Enumerated(EnumType.STRING)
    var category: Category, // e.g. FURNITURE, ELECTRONICS, etc.

    @Enumerated(EnumType.STRING)
    var donationOrRent: DonationOrRent, // Donation or Rent status

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User // User who created the listing
)






