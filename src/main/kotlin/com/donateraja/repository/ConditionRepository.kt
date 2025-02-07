package com.donateraja.repository

import com.donateraja.entity.Condition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConditionRepository : JpaRepository<Condition, Long>
