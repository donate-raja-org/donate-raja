package com.donateraja.service

import com.donateraja.entity.user.User
import com.donateraja.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User? = when {
            username.contains("@") -> userRepository.findByEmail(username)
            username.all { it.isDigit() } -> {
                userRepository.findByPhoneNumber(username)
                    ?: username.toLongOrNull()?.let { userRepository.findById(it).orElse(null) }
            }
            else -> null
        }

        return user ?: throw UsernameNotFoundException("User not found with identifier: $username")
    }
}
