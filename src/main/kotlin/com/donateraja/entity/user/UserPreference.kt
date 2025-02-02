package com.donateraja.entity.user

import jakarta.persistence.*


@Entity
@Table(name = "user_preferences")
data class UserPreference(
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User,

    val receiveNotifications: Boolean,

    val showProfilePublicly: Boolean,

    val preferredLanguage: String
)