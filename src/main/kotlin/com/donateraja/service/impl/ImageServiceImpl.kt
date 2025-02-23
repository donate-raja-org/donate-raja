package com.donateraja.service.impl

import com.backblaze.b2.client.contentSources.B2ContentTypes
import com.donateraja.common.BackblazeUtil
import com.donateraja.common.exception.ServiceException
import com.donateraja.common.util.ImageValidator
import com.donateraja.service.ImageService
import org.slf4j.MDC
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class ImageServiceImpl(private val backblazeUtil: BackblazeUtil, private val imageValidator: ImageValidator) : ImageService {

    override fun uploadImages(files: List<MultipartFile>, uploadType: String): List<String> {
        val userId = getUserIdFromContext()
        return files.mapIndexed { index, file ->
            // Use mapIndexed for index
            imageValidator.validateImageFile(
                file = file,
                uploadType = uploadType,
                index = index
            )
            backblazeUtil.uploadFile(
                fileBytes = file.bytes,
                fileName = "users/$userId/${UUID.randomUUID()}.${file.extension}",
                contentType = file.contentType ?: B2ContentTypes.B2_AUTO
            )
        }
    }

    override fun uploadImages(files: List<MultipartFile>): List<String> {
        val userId = getUserIdFromContext()
        return files.mapIndexed { index, file ->
            // Use mapIndexed for index
            imageValidator.validateImageFile(
                file = file,
                uploadType = "items", // Default upload type
                index = index
            )
            backblazeUtil.uploadFile(
                fileBytes = file.bytes,
                fileName = "users/$userId/${UUID.randomUUID()}.${file.extension}",
                contentType = file.contentType ?: B2ContentTypes.B2_AUTO
            )
        }
    }

    override fun uploadProfilePicture(file: MultipartFile): String {
        val userId = getUserIdFromContext()
        imageValidator.validateImageFile(
            file = file,
            uploadType = "profile", // Explicit upload type
            index = null // No index for single file
        )
        return backblazeUtil.uploadFile(
            fileBytes = file.bytes,
            fileName = "users/$userId/profile/${UUID.randomUUID()}.${file.extension}",
            contentType = file.contentType ?: "image/jpeg"
        )
    }

    private fun getUserIdFromContext(): String = MDC.get("user_id")
        ?: throw ServiceException(HttpStatus.UNAUTHORIZED, "User context not found")

    private val MultipartFile.extension: String
        get() = originalFilename?.substringAfterLast('.', "")
            ?: throw ServiceException(HttpStatus.BAD_REQUEST, "Invalid file extension")
}
