package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.annotation.RequireAdmin
import com.donateraja.domain.banner.BannerDTO
import com.donateraja.domain.banner.BannerResponseDTO
import com.donateraja.service.BannerService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/banners")
@Tag(name = "Banners", description = "APIs related to Weekly Donation Targets and Featured Causes")
class BannerController(private val bannerService: BannerService) {

    @GetMapping
    @ApiOperationWithCustomResponses(
        summary = "Get active banners",
        description = "Retrieves all active banners for donation campaigns.",
        successSchema = BannerResponseDTO::class
    )
    fun getActiveBanners(): ResponseEntity<List<BannerResponseDTO>> = ResponseEntity.ok(bannerService.getActiveBanners())

    @PostMapping
    @RequireAdmin
    @ApiOperationWithCustomResponses(
        summary = "Create a new banner",
        description = "Allows an admin to create a new banner.",
        successSchema = BannerResponseDTO::class
    )
    fun createBanner(@RequestBody bannerDTO: BannerDTO): ResponseEntity<BannerResponseDTO> =
        ResponseEntity.ok(bannerService.createBanner(bannerDTO))

    @PatchMapping("/{id}")
    @RequireAdmin
    @ApiOperationWithCustomResponses(
        summary = "Update a banner",
        description = "Allows an admin to update a banner.",
        successSchema = BannerResponseDTO::class
    )
    fun updateBanner(@PathVariable id: Long, @RequestBody bannerDTO: BannerDTO): ResponseEntity<BannerResponseDTO> =
        ResponseEntity.ok(bannerService.updateBanner(id, bannerDTO))

    @DeleteMapping("/{id}")
    @RequireAdmin
    @ApiOperationWithCustomResponses(
        summary = "Delete a banner",
        description = "Allows an admin to delete a banner.",
        successSchema = Void::class
    )
    fun deleteBanner(@PathVariable id: Long): ResponseEntity<Void> {
        bannerService.deleteBanner(id)
        return ResponseEntity.ok().build()
    }
}
