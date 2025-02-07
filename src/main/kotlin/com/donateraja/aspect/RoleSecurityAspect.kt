package com.donateraja.aspect

import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Aspect
@Component
class RoleSecurityAspect(private val request: HttpServletRequest) {

    // Public APIs (No authentication required)
    @Before("@within(com.donateraja.annotation.PublicAccess) || @annotation(com.donateraja.annotation.PublicAccess)")
    fun allowPublicAccess() {
        // No security check needed
    }

    // User Required (Works at both Class & Method level)
    @Before("@within(com.donateraja.annotation.RequireUser) || @annotation(com.donateraja.annotation.RequireUser)")
    fun checkUserAccess() {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || authentication.principal !is UserDetails) {
            throw AccessDeniedException("Unauthorized: User login required")
        }
    }

    // Admin Required
    @Before("@within(com.donateraja.annotation.RequireAdmin) || @annotation(com.donateraja.annotation.RequireAdmin)")
    fun checkAdminAccess() {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || authentication.principal !is UserDetails) {
            throw AccessDeniedException("Unauthorized: Admin login required")
        }

        val authorities = authentication.authorities.map { it.authority }
        if (!authorities.contains("ROLE_ADMIN")) {
            throw AccessDeniedException("Forbidden: Admin role required")
        }
    }

    // User OR Admin Required
    @Before("@within(com.donateraja.annotation.RequireUserOrAdmin) || @annotation(com.donateraja.annotation.RequireUserOrAdmin)")
    fun checkUserOrAdminAccess() {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || authentication.principal !is UserDetails) {
            throw AccessDeniedException("Unauthorized: User or Admin login required")
        }

        val authorities = authentication.authorities.map { it.authority }
        if (!authorities.contains("ROLE_USER") && !authorities.contains("ROLE_ADMIN")) {
            throw AccessDeniedException("Forbidden: Access requires User or Admin role")
        }
    }
}
