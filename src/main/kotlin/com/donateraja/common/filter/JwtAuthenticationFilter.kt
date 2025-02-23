package com.donateraja.common.filter

import com.donateraja.common.util.JwtUtil
import com.donateraja.common.util.UserLookupUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(private val jwtUtil: JwtUtil, private val userDetailsService: UserLookupUtil) :
    OncePerRequestFilter() {

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

                val email = jwtUtil.extractUsername(token) // Extract email from token
                val claims = jwtUtil.extractAllClaims(token)

                if (SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = userDetailsService.loadUserByUsername(email) // Fetch UserDetails

                    val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    SecurityContextHolder.getContext().authentication = auth

                    // Store user ID in request attributes for controllers to access
                    request.setAttribute("user_id", userDetails.username)
                }
            } catch (e: Exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token: ${e.message}")
                return
            }
        }

        chain.doFilter(request, response)
    }
}
