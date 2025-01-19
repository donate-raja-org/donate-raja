package com.donateraja.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig ()  {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // Configure security for Swagger UI and OpenAPI access
        http
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui/index.html")  // Swagger UI and docs access
                    .permitAll()  // Allow public access to these endpoints
                    .anyRequest()
                    .authenticated()  // Require authentication for other endpoints
            }

        // Instead of disabling HTTP Basic and form login, simply leave them out.
        // No need for .httpBasic().disable() or .formLogin().disable().

        return http.build() // Return the configured SecurityFilterChain
    }
}