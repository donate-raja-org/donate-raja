package com.donateraja.common

import com.backblaze.b2.client.B2StorageClient
import com.backblaze.b2.client.B2StorageClientFactory
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource
import com.backblaze.b2.client.contentSources.B2ContentTypes
import com.backblaze.b2.client.contentSources.B2FileContentSource
import com.backblaze.b2.client.exceptions.B2Exception
import com.backblaze.b2.client.structures.B2UploadFileRequest
import com.donateraja.configuration.BackblazeConfig
import org.springframework.stereotype.Component
import java.io.File
import java.security.MessageDigest

@Component
class BackblazeUtil(private val backblazeConfig: BackblazeConfig) {

    private val b2Client: B2StorageClient by lazy {
        B2StorageClientFactory.createDefaultFactory().create(
            backblazeConfig.applicationKeyId,
            backblazeConfig.applicationKey,
            "donate-raja"
        )
    }

    fun uploadFile(fileBytes: ByteArray, fileName: String, contentType: String = B2ContentTypes.B2_AUTO): String {
        val sha1 = calculateSha1(fileBytes)
        val contentSource = B2ByteArrayContentSource.builder(fileBytes).setSha1OrNull(sha1)
            .build()
        val request = B2UploadFileRequest.builder(backblazeConfig.bucketId, fileName, contentType, contentSource)
            .build()
        b2Client.uploadSmallFile(request)
        return generatePublicUrl(fileName)
    }

    private fun calculateSha1(bytes: ByteArray): String = MessageDigest.getInstance("SHA-1")
        .digest(bytes)
        .joinToString("") { "%02x".format(it) }

    fun uploadFile(filePath: String, fileName: String): String {
        try {
            val file = File(filePath)
            val contentSource = B2FileContentSource.build(file)
            val uploadRequest = B2UploadFileRequest
                .builder(backblazeConfig.bucketId, fileName, B2ContentTypes.B2_AUTO, contentSource)
                .build()

            b2Client.uploadSmallFile(uploadRequest)
            return generatePublicUrl(fileName)
        } catch (e: B2Exception) {
            throw RuntimeException("Failed to upload file: ${e.message}", e)
        }
    }

    fun uploadFiles(filePaths: List<String>, fileNames: List<String>): List<String> {
        if (filePaths.size != fileNames.size) {
            throw IllegalArgumentException("File paths and file names must have the same size.")
        }

        val uploadedUrls = mutableListOf<String>()
        try {
            for (i in filePaths.indices) {
                val filePath = filePaths[i]
                val fileName = fileNames[i]
                val publicUrl = uploadFile(filePath, fileName)
                uploadedUrls.add(publicUrl)
                println("Successfully uploaded file: $fileName")
            }
        } catch (e: B2Exception) {
            throw RuntimeException("Failed to upload files: ${e.message}", e)
        }

        return uploadedUrls
    }

    fun generatePublicUrl(fileName: String): String = "${backblazeConfig.publicBaseUrl}/$fileName"

    fun deleteFile(fileName: String) {
        try {
            val fileVersion = b2Client.getFileInfoByName(backblazeConfig.bucketId, fileName)
            b2Client.deleteFileVersion(fileVersion.fileName, fileVersion.fileId)
        } catch (e: B2Exception) {
            throw RuntimeException("Failed to delete file: ${e.message}", e)
        }
    }

    fun deleteFiles(fileNames: List<String>) {
        try {
            fileNames.forEach { fileName ->
                val fileVersion = b2Client.getFileInfoByName(backblazeConfig.bucketId, fileName)
                b2Client.deleteFileVersion(fileVersion.fileName, fileVersion.fileId)
                println("Successfully deleted file: $fileName")
            }
        } catch (e: B2Exception) {
            throw RuntimeException("Failed to delete files: ${e.message}", e)
        }
    }
}
