package com.donateraja.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.util.*

@Configuration
class JwtTokenConfig(
    @Value("\${spring.security.jwt.base64-signing-key}")
    val base64Secret: String,

    @Value("\${spring.security.jwt.access-token-expiration}")
    val accessTokenExpiration: String,

    @Value("\${spring.security.jwt.refresh-token-expiration}")
    val refreshTokenExpiration: String,

    @Value("\${spring.security.jwt.issuer}")
    val issuer: String
) {

    private val logger: Logger = LoggerFactory.getLogger(JwtTokenConfig::class.java)

    init {
        // Debug: Print values for verification (remove in production)
        logger.debug("Loaded base64Secret: $base64Secret")
        logger.debug("Loaded accessTokenExpiration: $accessTokenExpiration")
        logger.debug("Loaded refreshTokenExpiration: $refreshTokenExpiration")
        logger.debug("Loaded issuer: $issuer")

        // Ensure the base64 secret is at least 256 bits (32 bytes)
        require(Base64.getDecoder().decode(base64Secret).size >= 32) {
            "Signing key must be at least 256-bit (32 bytes)"
        }
    }

    /**
     * Parse the duration string (e.g., 1h, 7d) into a Duration object.
     */
    fun parseDuration(duration: String): Duration {
        return when {
            duration.endsWith("h") -> Duration.ofHours(duration.replace("h", "").toLong())
            duration.endsWith("d") -> Duration.ofDays(duration.replace("d", "").toLong())
            else -> throw IllegalArgumentException("Invalid duration format")
        }
    }
}
