package com.donateraja.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class BackblazeConfig(
    @Value("\${backblaze.applicationKeyId}") val applicationKeyId: String,
    @Value("\${backblaze.applicationKey}") val applicationKey: String,
    @Value("\${backblaze.bucketId}") val bucketId: String,
    @Value("\${backblaze.publicBaseUrl}") val publicBaseUrl: String
)
