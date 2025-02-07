package com.donateraja.common.util

import com.donateraja.configuration.JwtTokenConfig
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service
import java.security.Key
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class JwtUtil(
    private val jwtTokenConfig: JwtTokenConfig
) {

    // Generate JWT Access Token using jjwt
    fun generateAccessToken(authentication: Authentication): String {
        return buildToken(
            userId = authentication.name,
            authorities = authentication.authorities,
            expiration = parseDuration(jwtTokenConfig.accessTokenExpiration)
        )
    }

    // Generate JWT Refresh Token using jjwt
    fun generateRefreshToken(authentication: Authentication): String {
        return buildToken(
            userId = authentication.name,
            authorities = emptySet(),  // Refresh tokens usually don't carry roles
            expiration = parseDuration(jwtTokenConfig.refreshTokenExpiration)
        )
    }

    // Build JWT Token using jjwt
    private fun buildToken(
        userId: String,
        authorities: Collection<GrantedAuthority>,
        expiration: Duration
    ): String {
        val roles = if (authorities.isEmpty()) listOf("ROLE_USER") else authorities.map { it.authority }

        val key: Key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtTokenConfig.base64Secret))

        return Jwts.builder()
            .setIssuer(jwtTokenConfig.issuer)
            .setSubject(userId)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(expiration)))
            .claim("roles", roles)  // Ensure this claim is consistent
            .signWith(key, SignatureAlgorithm.HS256)  // Use HMAC with SHA-256
            .compact()
    }

    // Parse expiration string (e.g., "1h", "2d", etc.) to Duration
    private fun parseDuration(durationString: String): Duration {
        val pattern = Regex("(\\d+)([hmsd])")
        val matchResult = pattern.find(durationString)
        if (matchResult != null) {
            val amount = matchResult.groupValues[1].toLong()
            val unit = matchResult.groupValues[2]
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

    // Decode JWT token using jjwt
    fun validateToken(token: String): Map<String, Any> {
        val key: Key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtTokenConfig.base64Secret))

        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    // Method to extract specific claim
    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    // Method to check if the token has expired
    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    // Method to extract expiration date from the token
    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    // Method to extract the username (subject) from the token
    fun extractUsername(token: String): String {
        return extractClaim(token) { it.subject }
    }

    // Method to extract the username (subject) from the token
    fun extractUserId(token: String): String {
        return extractClaim(token) { it.subject }
    }

    // Method to validate the token
    fun validateToken(token: String, username: String): Boolean {
        return (username == extractUsername(token)) && !isTokenExpired(token)
    }

    fun extractUserIdFromBearerToken(authorizationHeader: String?): String? {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null
        }
        val token = authorizationHeader.substring("Bearer ".length)
        return try {
            parseTokenToExtractUsername(token)
        } catch (ex: Exception) {
            null
        }
    }

    private fun parseTokenToExtractUsername(token: String): String? {
        return try {
            val key: Key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtTokenConfig.base64Secret))
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
            claims.subject // Typically username or user ID in the token
        } catch (ex: Exception) {
            null
        }
    }

    private fun extractAllClaims(token: String): Claims {
        val key: Key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtTokenConfig.base64Secret))
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}

