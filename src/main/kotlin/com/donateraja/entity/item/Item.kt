package com.donateraja.entity.item

import com.donateraja.entity.constants.Category
import com.donateraja.entity.constants.Condition
import com.donateraja.entity.constants.DonationOrRent
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

@Entity
@Table(name = "items")
class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var itemName: String = ""

    @Column(nullable = false)
    var description: String = ""

    @Enumerated(EnumType.STRING)
    var condition: Condition = Condition.NEW

    var price: Double = 0.0

    var location: String = ""

    @Enumerated(EnumType.STRING)
    var category: Category = Category.ELECTRONICS

    @Enumerated(EnumType.STRING)
    var donationOrRent: DonationOrRent = DonationOrRent.RENT

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User // Using lateinit to prevent initialization issues

    constructor() // No-arg constructor for Hibernate

    constructor(
        id: Long,
        itemName: String,
        description: String,
        condition: Condition,
        price: Double,
        location: String,
        category: Category,
        donationOrRent: DonationOrRent,
        user: User
    ) {
        this.id = id
        this.itemName = itemName
        this.description = description
        this.condition = condition
        this.price = price
        this.location = location
        this.category = category
        this.donationOrRent = donationOrRent
        this.user = user
    }
}
