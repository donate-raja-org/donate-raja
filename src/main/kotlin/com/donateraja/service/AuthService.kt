package com.donateraja.service

import com.donateraja.common.exception.ServiceException
import com.donateraja.common.util.JwtUtil
import com.donateraja.entity.user.User
import com.donateraja.model.user.UserRegistrationDto
import com.donateraja.repository.AddressRepository
import com.donateraja.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
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
    @Throws(ServiceException::class)
    fun registerUser(userRegistrationDto: UserRegistrationDto): String {
        // Uncomment and add validation for existing email if needed
        // if (userRepository.existsByEmail(userRegistrationDto.email)) {
        //     throw IllegalArgumentException("User with email ${userRegistrationDto.email} already exists")
        // }

        val user = User(
            email = userRegistrationDto.email,
            password = passwordEncoder.encode(userRegistrationDto.password),
            username = userRegistrationDto.username,
            firstName = userRegistrationDto.firstName,
            lastName = userRegistrationDto.lastName,
            phoneNumber = userRegistrationDto.phoneNumber,
        )

        // Save user (uncomment if needed)
        // val savedUser = userRepository.save(user)

        // Add address if needed (uncomment and modify if needed)
        // val address = Address(user = savedUser, pincode = userRegistrationDto.pincode)
        // addressRepository.save(address)

        // Manually create authentication object for generating JWT
        val authentication = UsernamePasswordAuthenticationToken(user.username, user.password)
        return jwtUtil.generateAccessToken(authentication)
    }

    fun loginUser(email: String, password: String): String {
        try {
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(email, password)
            )

            // On successful authentication, generate JWT
            val userDetails = authentication.principal as UserDetails
            val user = userRepository.findByEmail(userDetails.username)
                ?: throw BadCredentialsException("Invalid credentials")

            return jwtUtil.generateAccessToken(authentication) // Adjust method if needed
        } catch (e: Exception) {
            throw BadCredentialsException("Invalid credentials")
        }
    }

    fun validateToken(token: String): Map<String, Any> {
        return jwtUtil.validateToken(token)
    }
}
