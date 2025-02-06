package com.donateraja.entity.user


import jakarta.persistence.*

@Entity
@Table(name = "user_roles")
class UserRole(  // ✅ Regular class prevents infinite recursion
    @EmbeddedId
    var id: UserRoleId,

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
)


