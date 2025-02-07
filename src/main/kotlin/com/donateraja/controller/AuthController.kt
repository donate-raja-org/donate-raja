package com.donateraja.controller

import com.donateraja.common.exception.ServiceException
import com.donateraja.domain.auth.AuthRequest
import com.donateraja.domain.auth.AuthResponse
import com.donateraja.model.user.UserRegistrationDto
import com.donateraja.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    @Throws(ServiceException::class)
    fun register(@RequestBody @Valid userRegistrationDto: UserRegistrationDto): ResponseEntity<AuthResponse> {
        val token = authService.registerUser(userRegistrationDto)
        return ResponseEntity.ok(token)
    }

    @PostMapping("/login")
    @Throws(ServiceException::class)
    fun login(@RequestBody @Valid authRequest: AuthRequest): ResponseEntity<AuthResponse> {
        val token = authService.loginUser(authRequest.emailIdOrPhoneNumberOrCustomerId, authRequest.password)
        return ResponseEntity.ok(token)
    }
}
