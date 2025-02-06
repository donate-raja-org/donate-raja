package com.donateraja.service

import com.donateraja.common.exception.ServiceException
import com.donateraja.common.util.JwtUtil
import com.donateraja.domain.auth.AuthResponse
import com.donateraja.entity.user.Address
import com.donateraja.entity.user.User
import com.donateraja.model.user.UserRegistrationDto
import com.donateraja.repository.AddressRepository
import com.donateraja.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val addressRepository: AddressRepository,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    @Transactional
    fun registerUser(userRegistrationDto: UserRegistrationDto): AuthResponse {
        if (userRepository.existsByEmail(userRegistrationDto.email)) {
            throw ServiceException(HttpStatus.CONFLICT, "User with email ${userRegistrationDto.email} already exists")
        }

        return try {
            val user = User(
                email = userRegistrationDto.email,
                password = passwordEncoder.encode(userRegistrationDto.password),
                username = userRegistrationDto.username,
                firstName = userRegistrationDto.firstName,
                lastName = userRegistrationDto.lastName,
                phoneNumber = userRegistrationDto.phoneNumber
            )

            val savedUser = userRepository.save(user)

            // Save address if provided
            if (!userRegistrationDto.pincode.isNullOrEmpty()) {
                val address = Address(user = savedUser, pincode = userRegistrationDto.pincode)
                addressRepository.save(address)
            }

            // Authenticate the user to generate the token
            val authentication = UsernamePasswordAuthenticationToken(user.email, user.password)
            val authResponse = jwtUtil.generateAccessToken(authentication)

            authResponse
        } catch (e: Exception) {
            throw ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed: ${e.message}")
        }
    }

    fun loginUser(emailOrPhone: String, password: String): AuthResponse {
        try {
            // Find user by email or phone number
            val user = userRepository.findByEmail(emailOrPhone)
                ?: userRepository.findByPhoneNumber(emailOrPhone)
                ?: throw ServiceException(HttpStatus.NOT_FOUND, "User not found")

            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(user.email, password)
            )

            return jwtUtil.generateAccessToken(authentication)

        } catch (e: BadCredentialsException) {
            throw ServiceException(HttpStatus.UNAUTHORIZED, "Invalid email or password")
        } catch (e: Exception) {
            throw ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Login failed: ${e.message}")
        }
    }
}
