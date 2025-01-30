package com.donateraja.service

import com.donateraja.entity.User
import com.donateraja.entity.constants.Role
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit


@Service
class JwtService(
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder
) {

    fun generateJwt(user: User): String {
        // Build JWT claims
        val claims = JwtClaimsSet.builder()
            .issuer("donate-raja") // Issuer of the token
            .subject(user.phoneNumber) // Subject (e.g., user identifier)
            .claim("roles", Role.ROLE_USER ) // Custom claim for roles
            .issuedAt(Instant.now()) // Token issuance time
            .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS)) // Token expiration time (1 hour)
            .build()

        // Encode the JWT
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    fun decodeJwt(token: String): Map<String, Any> {
        // Decode the JWT
        val jwt = jwtDecoder.decode(token)
        return jwt.claims
    }
}
