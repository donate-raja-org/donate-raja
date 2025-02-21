package com.donateraja.service.impl

import com.donateraja.common.exception.ServiceException
import com.donateraja.common.util.JwtUtil
import com.donateraja.domain.auth.AuthRequest
import com.donateraja.domain.auth.AuthResponse
import com.donateraja.entity.user.Address
import com.donateraja.entity.user.User
import com.donateraja.model.user.UserRegistrationDto
import com.donateraja.repository.AddressRepository
import com.donateraja.repository.UserRepository
import com.donateraja.service.AuthService
import org.slf4j.MDC
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val addressRepository: AddressRepository,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager
) : AuthService {
    private val passwordEncoder = BCryptPasswordEncoder()

    @Transactional
    override fun registerUser(userRegistrationDto: UserRegistrationDto): AuthResponse {
        if (userRepository.existsByEmail(userRegistrationDto.email!!)) {
            throw ServiceException(HttpStatus.CONFLICT, "User with email ${userRegistrationDto.email} already exists")
        }
        return try {
            val user = User(
                email = userRegistrationDto.email!!,
                password = passwordEncoder.encode(userRegistrationDto.password),
                username = userRegistrationDto.username!!,
                firstName = userRegistrationDto.firstName,
                lastName = userRegistrationDto.lastName,
                phoneNumber = userRegistrationDto.phoneNumber
            )
            val savedUser = userRepository.save(user)
            if (!userRegistrationDto.pincode.isNullOrEmpty()) {
                val address = Address(user = savedUser, pincode = userRegistrationDto.pincode!!)
                addressRepository.save(address)
            }
            val authentication = UsernamePasswordAuthenticationToken(user.email, user.password)
            val authResponse = jwtUtil.generateAccessToken(authentication)
            authResponse
        } catch (e: Exception) {
            throw ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed: ${e.message}")
        }
    }

    @Throws(ServiceException::class)
    override fun refreshToken(refreshToken: String): AuthResponse = try {
        val userId = MDC.get("user_id").toLong()
        val user = userRepository.findById(userId)
        if (userRepository.existsByEmail(user.get().email)) {
            throw ServiceException(HttpStatus.CONFLICT, "User with refresh token failed. user not exist")
        }
        val authentication = UsernamePasswordAuthenticationToken(user.get().email, user.get().password)
        val authResponse = jwtUtil.generateAccessToken(authentication)
        authResponse
    } catch (e: Exception) {
        throw ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed: ${e.message}")
    }

    override fun loginUser(authRequest: AuthRequest): AuthResponse {
        val identifier = authRequest.identifier!!
        val password = authRequest.password!!
        val user: User? = when {
            identifier.contains("@") -> userRepository.findByEmail(identifier)
            identifier.all { it.isDigit() } -> {
                userRepository.findByPhoneNumber(identifier)
                    ?: identifier.toLongOrNull()?.let { userRepository.findById(it).orElse(null) }
            }
            else -> throw ServiceException(HttpStatus.UNAUTHORIZED, "UserId or Email or Phone is invalid")
        }
        if (user == null) {
            throw ServiceException(HttpStatus.UNAUTHORIZED, "UserId or Email or Phone is invalid")
        }
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(identifier, password)
        )
        return jwtUtil.generateAccessToken(authentication)
    }

    override fun logoutUser(token: String): String {
        if (!jwtUtil.validateToken(token)) {
            throw ServiceException(HttpStatus.UNAUTHORIZED, "Invalid or expired token")
        }
        val rawToken = token.replace("Bearer ", "")
        jwtUtil.addToBlacklist(rawToken) // Actual invalidation
        return "Success"
    }

    override fun resendVerificationEmail(email: String) {
        TODO("Not yet implemented")
    }

    override fun verifyEmail(token: String) {
        TODO("Not yet implemented")
    }
}
