package com.donateraja.repository

import com.donateraja.entity.user.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : JpaRepository<Address, Long> {
    fun findByUserId(userId: Long): List<Address>
}