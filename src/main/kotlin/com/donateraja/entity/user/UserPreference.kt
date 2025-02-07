package com.donateraja.entity.user

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

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
