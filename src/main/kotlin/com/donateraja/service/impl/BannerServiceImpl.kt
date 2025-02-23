package com.donateraja.service.impl

import com.donateraja.common.exception.ServiceException
import com.donateraja.domain.banner.BannerDTO
import com.donateraja.domain.banner.BannerResponseDTO
import com.donateraja.entity.banners.Banner
import com.donateraja.repository.BannerRepository
import com.donateraja.service.BannerService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BannerServiceImpl(private val bannerRepository: BannerRepository) : BannerService {

    override fun getActiveBanners(): List<BannerResponseDTO> {
        val activeBanners = bannerRepository.findByEndDateAfter(LocalDateTime.now())
        return activeBanners.map { banner ->
            BannerResponseDTO(
                id = banner.id,
                title = banner.title,
                description = banner.description,
                imageUrl = banner.imageUrl,
                startDate = banner.startDate,
                endDate = banner.endDate,
                createdAt = banner.createdAt
            )
        }
    }

    @Transactional
    override fun createBanner(bannerDTO: BannerDTO): BannerResponseDTO {
        val newBanner = bannerRepository.save(
            Banner(
                title = bannerDTO.title,
                description = bannerDTO.description,
                imageUrl = bannerDTO.imageUrl,
                startDate = bannerDTO.startDate,
                endDate = bannerDTO.endDate
            )
        )
        return BannerResponseDTO(
            id = newBanner.id,
            title = newBanner.title,
            description = newBanner.description,
            imageUrl = newBanner.imageUrl,
            startDate = newBanner.startDate,
            endDate = newBanner.endDate,
            createdAt = newBanner.createdAt
        )
    }

    @Transactional
    override fun updateBanner(id: Long, bannerDTO: BannerDTO): BannerResponseDTO {
        val existingBanner = bannerRepository.findById(id)
            .orElseThrow { ServiceException(HttpStatus.NOT_FOUND, "Banner not found") }

        existingBanner.title = bannerDTO.title
        existingBanner.description = bannerDTO.description
        existingBanner.imageUrl = bannerDTO.imageUrl
        existingBanner.startDate = bannerDTO.startDate
        existingBanner.endDate = bannerDTO.endDate

        val updatedBanner = bannerRepository.save(existingBanner)
        return BannerResponseDTO(
            id = updatedBanner.id,
            title = updatedBanner.title,
            description = updatedBanner.description,
            imageUrl = updatedBanner.imageUrl,
            startDate = updatedBanner.startDate,
            endDate = updatedBanner.endDate,
            createdAt = updatedBanner.createdAt
        )
    }

    @Transactional
    override fun deleteBanner(id: Long) {
        if (!bannerRepository.existsById(id)) {
            throw ServiceException(HttpStatus.NOT_FOUND, "Banner not found")
        }
        bannerRepository.deleteById(id)
    }
}
