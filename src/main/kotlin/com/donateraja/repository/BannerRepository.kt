package com.donateraja.repository

import com.donateraja.entity.banners.Banner
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface BannerRepository : JpaRepository<Banner, Long> {
    fun findByEndDateAfter(now: LocalDateTime): List<Banner>
}
