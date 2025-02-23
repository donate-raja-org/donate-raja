package com.donateraja.common.util

import com.donateraja.common.exception.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class ImageValidator {

    fun validateUploadType(uploadType: String) {
        val allowedTypes = setOf("items", "profile", "documents")
        if (uploadType !in allowedTypes) {
            throw ServiceException(
                HttpStatus.BAD_REQUEST,
                "Invalid upload type. Allowed values: ${allowedTypes.joinToString()}"
            )
        }
    }

    fun validateImageFile(file: MultipartFile, uploadType: String, index: Int?) {
        val prefix = index?.let { "File #${it + 1}: " } ?: ""

        // Check empty file
        if (file.isEmpty) {
            throw ServiceException(HttpStatus.BAD_REQUEST, "${prefix}File is empty")
        }

        // Validate file size
        val maxSize = when (uploadType) {
            "profile" -> 2_000_000
            "documents" -> 10_000_000
            else -> 5_000_000 // items and others
        }
        if (file.size > maxSize) {
            throw ServiceException(
                HttpStatus.PAYLOAD_TOO_LARGE,
                "${prefix}File size exceeds ${maxSize / 1_000_000}MB limit"
            )
        }

        // Validate content type
        val allowedTypes = when (uploadType) {
            "profile" -> setOf("image/jpeg", "image/png")
            "items" -> setOf("image/jpeg", "image/png", "image/webp")
            "documents" -> setOf("application/pdf", "text/plain")
            else -> throw ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid upload type configuration")
        }

        val contentType = file.contentType ?: throw ServiceException(
            HttpStatus.BAD_REQUEST,
            "${prefix}File type not recognized"
        )

        if (contentType !in allowedTypes) {
            throw ServiceException(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "${prefix}Invalid file type for $uploadType. Allowed: ${allowedTypes.joinToString()}"
            )
        }

        // Validate filename
        val fileName = file.originalFilename ?: throw ServiceException(
            HttpStatus.BAD_REQUEST,
            "${prefix}File name is missing"
        )

        if (fileName.contains("..") || fileName.contains("/")) {
            throw ServiceException(
                HttpStatus.BAD_REQUEST,
                "${prefix}Invalid file name"
            )
        }
    }

    fun getFileExtension(file: MultipartFile): String = file.originalFilename
        ?.substringAfterLast('.', "")
        ?.takeIf { it.isNotBlank() }
        ?: throw ServiceException(
            HttpStatus.BAD_REQUEST,
            "File extension is missing or invalid"
        )
}
