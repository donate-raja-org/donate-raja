package com.donateraja.service.impl

import com.donateraja.common.exception.ServiceException
import com.donateraja.common.util.JwtUtil
import com.donateraja.domain.auth.AuthRequest
import com.donateraja.domain.auth.AuthResponse
import com.donateraja.domain.user.UserRegistrationRequest
import com.donateraja.entity.constants.Role
import com.donateraja.entity.user.Address
import com.donateraja.entity.user.User
import com.donateraja.entity.user.UserRole
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
    override fun registerUser(userRegistrationRequest: UserRegistrationRequest): AuthResponse {
        if (userRepository.existsByEmail(userRegistrationRequest.email!!)) {
            throw ServiceException(HttpStatus.CONFLICT, "User with email ${userRegistrationRequest.email} already exists")
        }

        return try {
            val user = User(
                email = userRegistrationRequest.email!!,
                password = passwordEncoder.encode(userRegistrationRequest.password),
                username = userRegistrationRequest.username!!,
                firstName = userRegistrationRequest.firstName,
                lastName = userRegistrationRequest.lastName,
                phoneNumber = userRegistrationRequest.phoneNumber
            )

            // ✅ Add role before saving
            user.roles.add(UserRole(user = user, role = Role.USER)) // Ensure this method exists in your User entity

            // ✅ Add address before saving (if provided)
            if (!userRegistrationRequest.pincode.isNullOrEmpty()) {
                val address = Address(user = user, pincode = userRegistrationRequest.pincode!!)
                user.addresses.add(address) // Assuming User has List<Address>
            }

            val savedUser = userRepository.save(user) // ✅ Save everything in one go

            val authentication = UsernamePasswordAuthenticationToken(user.email, user.password)
            val authResponse = jwtUtil.generateAccessToken(authentication)
            authResponse
        } catch (e: Exception) {
            throw ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed: ${e.message}")
        }
    }

    @Throws(ServiceException::class)
    override fun refreshToken(refreshToken: String): AuthResponse = try {
        val userId = MDC.get("user_id")
//        val user = userRepository.findByEmail(userId)
        if (!userRepository.existsByEmail(userId)) {
            throw ServiceException(HttpStatus.CONFLICT, "User with refresh token failed. user not exist")
        }
        val user = userRepository.findByEmail(userId)
        val authentication = UsernamePasswordAuthenticationToken(user!!.email, user!!.password)
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
