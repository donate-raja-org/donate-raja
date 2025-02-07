package com.donateraja.entity.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user_roles")
data class UserRole(
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Id
    @Column(name = "role", nullable = false)
    val role: String
)
