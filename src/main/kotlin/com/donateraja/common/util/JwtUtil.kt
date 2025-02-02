package com.donateraja.common.util

import com.donateraja.configuration.JwtTokenConfig
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.regex.Pattern

@Service
class JwtUtil(
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder,
    private val jwtTokenConfig: JwtTokenConfig
) {
    fun generateAccessToken(authentication: Authentication): String {
        return buildToken(
            subject = authentication.name,
            authorities = authentication.authorities,
            expiration = parseDuration(jwtTokenConfig.accessTokenExpiration)
        )
    }

    fun generateRefreshToken(authentication: Authentication): String {
        return buildToken(
            subject = authentication.name,
            authorities = emptySet(),
            expiration = parseDuration(jwtTokenConfig.refreshTokenExpiration)
        )
    }

    private fun buildToken(
        subject: String,
        authorities: Collection<GrantedAuthority>,
        expiration: Duration
    ): String {
        val claims = JwtClaimsSet.builder()
            .issuer(jwtTokenConfig.issuer)
            .subject(subject)
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(expiration))
            .claim("roles", authorities.map { it.authority })
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    // Parse expiration string (e.g., "1h", "2d", etc.) to Duration
    private fun parseDuration(durationString: String): Duration {
        val pattern = Pattern.compile("(\\d+)([hmsd])")
        val matcher = pattern.matcher(durationString)
        if (matcher.matches()) {
            val amount = matcher.group(1).toLong()
            val unit = matcher.group(2)
            return when (unit) {
                "h" -> Duration.ofHours(amount)
                "m" -> Duration.ofMinutes(amount)
                "s" -> Duration.ofSeconds(amount)
                "d" -> Duration.ofDays(amount)
                else -> throw IllegalArgumentException("Unsupported time unit")
            }
        }
        throw IllegalArgumentException("Invalid duration string format")
    }

    // Decode JWT token for validation or extraction of claims
    fun decodeJwt(token: String): Map<String, Any> {
        val jwt = jwtDecoder.decode(token)
        return jwt.claims
    }
}
