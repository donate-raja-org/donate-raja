package com.donateraja.controller

import com.donateraja.model.user.UserRegistrationDto
import com.donateraja.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestBody @Valid userRegistrationDto: UserRegistrationDto): ResponseEntity<String> {
        return try {
            val token = authService.registerUser(userRegistrationDto)
            ResponseEntity.ok("User registered successfully. Token: $token")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message ?: "Registration failed")
        }
    }

    @PostMapping("/login")
    fun login(@RequestParam email: String, @RequestParam password: String): ResponseEntity<String> {
        return try {
            val token = authService.loginUser(email, password)
            ResponseEntity.ok("Login successful. Token: $token")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message ?: "Login failed")
        }
    }


    @GetMapping("/validate-token")
    fun validateToken(@RequestHeader("Authorization") authHeader: String): ResponseEntity<String> {
        val token = authHeader.removePrefix("Bearer ").trim()

        return try {
            val decodedToken = authService.validateToken(token)
            // You can also check the validity and expiration here
            ResponseEntity.ok("Token is valid. Claims: $decodedToken")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Invalid or expired token: ${e.message}")
        }
    }
}
