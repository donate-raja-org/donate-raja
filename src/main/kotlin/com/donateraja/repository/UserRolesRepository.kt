package com.donateraja.repository

import com.donateraja.entity.constants.Role
import com.donateraja.entity.user.User
import com.donateraja.entity.user.UserRole
import org.springframework.data.jpa.repository.JpaRepository

interface UserRolesRepository : JpaRepository<UserRole, Long> {
    fun existsByUserAndRole(user: User, role: Role): Boolean
}
