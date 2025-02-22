package com.donateraja.service

import org.springframework.web.multipart.MultipartFile

interface ImageService {
    fun uploadImages(files: List<MultipartFile>, uploadType: String): List<String>
    fun uploadProfilePicture(file: MultipartFile): String
    fun uploadImages(files: List<MultipartFile>): List<String>
}
