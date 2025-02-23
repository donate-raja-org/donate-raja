package com.donateraja.common.filter

import com.donateraja.common.exception.ServiceException
import com.donateraja.common.util.JwtUtil
import com.donateraja.common.util.UserLookupUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerMapping
@Component
class JwtAuthenticationFilter(private val jwtUtil: JwtUtil, private val userDetailsService: UserLookupUtil) :
    OncePerRequestFilter() {

    @Throws(ServletException::class, java.io.IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE) as? HandlerMethod

        // ✅ If no handler found, skip authentication (e.g., static resources)
        if (handler == null) {
            chain.doFilter(request, response)
            return
        }

        // 🔹 Check if API requires authentication based on annotations
        val isProtected = handler.hasMethodAnnotation(com.donateraja.annotation.RequireUser::class.java) ||
            handler.hasMethodAnnotation(com.donateraja.annotation.RequireAdmin::class.java) ||
            handler.hasMethodAnnotation(com.donateraja.annotation.RequireUserOrAdmin::class.java)

        // ✅ If no security annotation is found, allow the request as public API
        if (!isProtected) {
            chain.doFilter(request, response)
            return
        }

        // 🔒 Authentication required → Check for Bearer token
        val authHeader = request.getHeader("Authorization")
        if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Bearer ")) {
            throw ServiceException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header")
        }

        val token = authHeader.substring(7)

        try {
            if (jwtUtil.isTokenExpired(token)) {
                throw ServiceException(HttpStatus.UNAUTHORIZED, "Token has expired")
            }

            val email = jwtUtil.extractUsername(token)

            if (SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userDetailsService.loadUserByUsername(email)

                val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                SecurityContextHolder.getContext().authentication = auth

                request.setAttribute("username", userDetails.username)
            }
        } catch (e: Exception) {
            throw ServiceException(HttpStatus.UNAUTHORIZED, "Invalid token: ${e.message}")
        }

        chain.doFilter(request, response)
    }
}
