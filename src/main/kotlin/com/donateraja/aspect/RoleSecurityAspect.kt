package com.donateraja.aspect

import com.donateraja.common.exception.ServiceException
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Aspect
@Component
class RoleSecurityAspect(private val request: HttpServletRequest) {

    private val logger = LoggerFactory.getLogger(RoleSecurityAspect::class.java)

    /**
     * Allows public access to methods or classes annotated with @PublicAccess.
     */
    @Before("@within(com.donateraja.annotation.PublicAccess) || @annotation(com.donateraja.annotation.PublicAccess)")
    fun allowPublicAccess() {
        // No security check needed for public endpoints
    }

    /**
     * Ensures that the request is authenticated as a regular user.
     */
    @Before("@within(com.donateraja.annotation.RequireUser) || @annotation(com.donateraja.annotation.RequireUser)")
    fun checkUserAccess() {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated || authentication.principal !is UserDetails) {
            logger.warn("Unauthorized user access attempt from IP: ${request.remoteAddr}")
            throw ServiceException(HttpStatus.UNAUTHORIZED, "Unauthorized: User login required")
        }
    }

    /**
     * Ensures that the request is authenticated as an admin user.
     */
    @Before("@within(com.donateraja.annotation.RequireAdmin) || @annotation(com.donateraja.annotation.RequireAdmin)")
    fun checkAdminAccess() {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated || authentication.principal !is UserDetails) {
            logger.warn("Unauthorized admin access attempt from IP: ${request.remoteAddr}")
            throw ServiceException(HttpStatus.UNAUTHORIZED, "Unauthorized: Admin login required")
        }

        val authorities = authentication.authorities.map { it.authority }
        if (!authorities.contains("ADMIN")) {
            logger.warn("Forbidden access attempt by user: ${authentication.name} from IP: ${request.remoteAddr}")
            throw ServiceException(HttpStatus.FORBIDDEN, "Forbidden: Admin role required")
        }
    }

    /**
     * Ensures that the request is authenticated as either a regular user or an admin.
     */
    @Before("@within(com.donateraja.annotation.RequireUserOrAdmin) || @annotation(com.donateraja.annotation.RequireUserOrAdmin)")
    fun checkUserOrAdminAccess() {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated || authentication.principal !is UserDetails) {
            logger.warn("Unauthorized user/admin access attempt from IP: ${request.remoteAddr}")
            throw ServiceException(HttpStatus.UNAUTHORIZED, "Unauthorized: User or Admin login required")
        }

        val authorities = authentication.authorities.map { it.authority }
        if (!authorities.contains("USER") && !authorities.contains("ADMIN")) {
            logger.warn("Forbidden access attempt by user: ${authentication.name} from IP: ${request.remoteAddr}")
            throw ServiceException(HttpStatus.FORBIDDEN, "Forbidden: Access requires User or Admin role")
        }
    }
}
