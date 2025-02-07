package com.donateraja.entity.user

import com.donateraja.entity.constants.Role
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.io.Serializable

@Embeddable
class UserRoleId(
    @Column(name = "user_id")
    var userId: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: Role = Role.ROLE_USER
) : Serializable {
    constructor() : this(0, Role.ROLE_USER)
}
