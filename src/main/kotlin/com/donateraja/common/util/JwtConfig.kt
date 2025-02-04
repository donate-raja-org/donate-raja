package com.donateraja.common.util

import com.donateraja.configuration.JwtTokenConfig
import com.nimbusds.jose.jwk.source.ImmutableSecret
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
class JwtConfig(private val jwtTokenConfig: JwtTokenConfig) {


    @Bean
    fun secretKey(): SecretKey {
        val decodedKey = Base64.getDecoder().decode("mXh0/Uotn5+BBVcIzhSxInXDVMXfp7zZPJiXu92WHrA=")
        println("Key size:-------- ${decodedKey.size}")  // Should output 32 bytes (256 bits)
        if (decodedKey.size != 32) {
            throw IllegalArgumentException("Signing key must be exactly 256 bits (32 bytes)")
        }
        return SecretKeySpec(decodedKey, "HmacSHA256")
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val secretKey = secretKey()
        return NimbusJwtEncoder(ImmutableSecret(secretKey))  // Use ImmutableSecret for HMAC
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val secretKey = secretKey()
        return NimbusJwtDecoder.withSecretKey(secretKey).build()  // Decoder for symmetric keys
    }
}
