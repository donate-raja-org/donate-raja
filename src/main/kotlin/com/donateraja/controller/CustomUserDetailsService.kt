// file: src/main/kotlin/com/donateraja/security/CustomUserDetailsService.kt
package com.donateraja.controller

import com.donateraja.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) 
            ?: throw UsernameNotFoundException("User not found with email: $username")

        return User.builder()
            .username(user.email)
            .password(user.password)
            .authorities(user.roles.map { SimpleGrantedAuthority(it.toString()) })
            .build()
    }
}