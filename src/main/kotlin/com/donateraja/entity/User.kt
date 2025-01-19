package com.donateraja.entity

import jakarta.persistence.*


@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Column(unique = true, nullable = false)
    var username: String,

    @Column(name = "first_name")
    var firstName: String,

    @Column(name = "last_name")
    var lastName: String,

    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    @Column(name = "profile_picture")
    var profilePicture: String? = null,

    @Column
    var address: String? = null,

    @Column(name = "pin_code")
    var pinCode: String? = null,

    @Column
    var role: String = "USER",

    @Column
    var status: String = "ACTIVE",

    @Column(name = "is_email_verified")
    var isEmailVerified: Boolean = false,

    @Column(name = "is_phone_verified")
    var isPhoneVerified: Boolean = false
)