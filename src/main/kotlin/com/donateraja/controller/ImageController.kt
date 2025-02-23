package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.service.ImageService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotBlank
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Validated
@RestController
@RequestMapping("/api/images")
@Tag(name = "Images", description = "APIs for uploading and retrieving images")
class ImageController(private val imageService: ImageService) {

    @PostMapping(
        value = ["/upload"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    @ApiOperationWithCustomResponses(
        summary = "Upload multiple images",
        description = "Upload images for items or other content. Max 5 files per request, 5MB each.",
        successSchema = List::class
    )
    fun uploadImages(
        @RequestParam("files") files: List<MultipartFile>,
        @RequestParam("uploadType") @NotBlank uploadType: String
    ): List<String> = imageService.uploadImages(files, uploadType)

    @PostMapping(
        value = ["/upload/profile"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    @ApiOperationWithCustomResponses(
        summary = "Upload profile picture",
        description = "Upload a single profile picture. Max size 2MB.",
        successSchema = String::class
    )
    fun uploadProfilePicture(@RequestParam("file") file: MultipartFile): String = imageService.uploadProfilePicture(file)
}
