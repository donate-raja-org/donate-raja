package com.donateraja.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtEncoder: JwtEncoder,
    @Value("\${jwt.expiration}") private val expiration: Long
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )

        val userDetails = userDetailsService.loadUserByUsername(request.username)
        val token = generateToken(userDetails)

        return ResponseEntity.ok(AuthResponse(token))
    }

    private fun generateToken(userDetails: UserDetails): String {
        val claims = JwtClaimsSet.builder()
            .issuer("donate-raja")
            .subject(userDetails.username)
            .claim("roles", userDetails.authorities.map { it.authority })
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusMillis(expiration))
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}

data class LoginRequest(val username: String, val password: String)
data class AuthResponse(val token: String)
