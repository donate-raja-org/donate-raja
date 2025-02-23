package com.donateraja.service

import com.donateraja.domain.banner.BannerDTO
import com.donateraja.domain.banner.BannerResponseDTO

interface BannerService {
    fun getActiveBanners(): List<BannerResponseDTO>
    fun createBanner(bannerDTO: BannerDTO): BannerResponseDTO
    fun updateBanner(id: Long, bannerDTO: BannerDTO): BannerResponseDTO
    fun deleteBanner(id: Long)
}
