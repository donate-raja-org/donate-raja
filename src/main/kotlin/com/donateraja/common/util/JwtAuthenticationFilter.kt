package com.donateraja.common.util

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {

    @Throws(ServletException::class, java.io.IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authHeader = request.getHeader("Authorization")

        if (!authHeader.isNullOrEmpty() && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)

            try {
                if (jwtUtil.isTokenExpired(token)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired")
                    return
                }

                val userId = jwtUtil.extractUsername(token)
                val claims = jwtUtil.extractAllClaims(token)

                if (SecurityContextHolder.getContext().authentication == null) {
                    val roles = (claims["roles"] as? List<*>)?.filterIsInstance<String>() ?: listOf("ROLE_USER")
                    val authorities = roles.map { SimpleGrantedAuthority(it) }

                    val auth = UsernamePasswordAuthenticationToken(userId, null, authorities)
                    SecurityContextHolder.getContext().authentication = auth

                    // Store user ID in request attributes for controllers to access
                    request.setAttribute("user_id", userId)
                }
            } catch (e: Exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token: ${e.message}")
                return
            }
        }

        chain.doFilter(request, response)
    }
}
