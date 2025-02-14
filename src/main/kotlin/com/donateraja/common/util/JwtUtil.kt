package com.donateraja.common.util

import com.donateraja.common.exception.ServiceException
import com.donateraja.configuration.JwtTokenConfig
import com.donateraja.domain.auth.AuthResponse
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service
import java.security.Key
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class JwtUtil(private val jwtTokenConfig: JwtTokenConfig) {

    private val key: Key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtTokenConfig.base64Secret))

    @Throws(ServiceException::class)
    fun generateAccessToken(authentication: Authentication): AuthResponse =
        buildToken(authentication.name, authentication.authorities, parseDuration(jwtTokenConfig.accessTokenExpiration))

    @Throws(ServiceException::class)
    fun generateRefreshToken(authentication: Authentication): AuthResponse =
        buildToken(authentication.name, emptySet(), parseDuration(jwtTokenConfig.refreshTokenExpiration))

    private fun buildToken(userId: String, authorities: Collection<GrantedAuthority>, expiration: Duration): AuthResponse {
        val roles = authorities.map { it.authority }.ifEmpty { listOf("ROLE_USER") }

        val token = Jwts.builder()
            .setIssuer(jwtTokenConfig.issuer)
            .setSubject(userId)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(expiration)))
            .claim("roles", roles)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return AuthResponse(token, expiration.toMillis(), roles, null)
    }

    fun validateToken(token: String): Boolean = try {
        val claims = extractAllClaims(token)
        if (claims.expiration.before(Date())) {
            throw ServiceException(HttpStatus.UNAUTHORIZED, "Token expired, please refresh")
        }
        true
    } catch (e: ExpiredJwtException) {
        throw ServiceException(HttpStatus.UNAUTHORIZED, "Token expired, please refresh")
    } catch (e: JwtException) {
        throw ServiceException(HttpStatus.FORBIDDEN, "Invalid token")
    }

    fun extractUsername(token: String): String = extractClaim(token) { it.subject }

    fun extractRoles(token: String): List<String> = try {
        extractClaim(token) { claims ->
            claims["roles"]?.let { it as List<*> }?.mapNotNull { it as? String } ?: emptyList()
        }
    } catch (e: Exception) {
        emptyList()
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T = claimsResolver(extractAllClaims(token))

    fun extractAllClaims(token: String): Claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body

    fun isTokenExpired(token: String): Boolean = try {
        val claims = extractAllClaims(token)
        claims.expiration.before(Date())
    } catch (e: ExpiredJwtException) {
        true
    }

    fun parseDuration(duration: String): Duration {
        val pattern = Regex("(\\d+)([hmsd])")
        val matchResult = pattern.find(duration) ?: throw IllegalArgumentException("Invalid duration format: $duration")
        val amount = matchResult.groupValues[1].toLong()
        return when (matchResult.groupValues[2]) {
            "h" -> Duration.ofHours(amount)
            "m" -> Duration.ofMinutes(amount)
            "s" -> Duration.ofSeconds(amount)
            "d" -> Duration.ofDays(amount)
            else -> throw IllegalArgumentException("Unsupported time unit in duration: $duration")
        }
    }

    fun generateExpiredToken(userId: String): String = Jwts.builder()
        .setIssuer(jwtTokenConfig.issuer)
        .setSubject(userId)
        .setIssuedAt(Date())
        .setExpiration(Date()) // Immediate expiry
        .signWith(key, SignatureAlgorithm.HS256)
        .compact()
}
