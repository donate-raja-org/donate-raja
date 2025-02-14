package com.donateraja.controller

import ApiOperationWithCustomResponses
import com.donateraja.domain.auth.AuthRequest
import com.donateraja.domain.auth.AuthResponse
import com.donateraja.model.user.UserRegistrationDto
import com.donateraja.service.AuthService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "APIs related to User Authentication, Login, and Registration")
class AuthController(private val authService: AuthService) {

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @ApiOperationWithCustomResponses(
        summary = "Register a new user",
        description = "Creates a new user account and returns an authentication token.",
        successSchema = AuthResponse::class
    )
    @PostMapping("/register")
    fun register(@RequestBody @Valid userRegistrationDto: UserRegistrationDto): ResponseEntity<AuthResponse> {
        logger.info("User registration attempt: ${userRegistrationDto.email}")

        val token = authService.registerUser(userRegistrationDto)

        logger.info("User registered successfully: ${userRegistrationDto.email}")

        return ResponseEntity.ok(token)
    }

    @ApiOperationWithCustomResponses(
        summary = "User login",
        description = "Authenticates user and returns JWT token.",
        successSchema = AuthResponse::class
    )
    @PostMapping("/login")
    fun login(@RequestBody @Valid authRequest: AuthRequest): ResponseEntity<AuthResponse> {
        logger.info("User login attempt: ${authRequest.identifier}")

        val token = authService.loginUser(authRequest.identifier, authRequest.password)

        logger.info("User logged in successfully: ${authRequest.identifier}")

        return ResponseEntity.ok(token)
    }

    @ApiOperationWithCustomResponses(summary = "User logout", description = "Logs out the user by clearing security context.")
    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        authService.logoutUser(token)
        return ResponseEntity.ok("Logout successful. Please discard the token on the client-side.")
    }
}
