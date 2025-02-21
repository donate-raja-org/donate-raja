package com.donateraja.repository

import com.donateraja.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByPhoneNumber(phoneNumber: String): User?

//    fun findById(id: Long): User?
    fun existsByEmail(email: String): Boolean
//    fun findById(userId: Long): Optional<User>

    @Query("SELECT u FROM User u WHERE u.email = :input OR u.phoneNumber = :input OR u.id = :input")
    fun findByAnyIdentifier(@Param("input") input: String): User?

//    @Query(
//        "INSERT INTO user_roles (user_id, role_id) SELECT u.id, r.id FROM User u JOIN Role r WHERE u.id = :userId AND r.name = 'ADMIN'"
//    )
//    fun addAdminRoleToUser(@Param("userId") userId: Long): Int
}
