package com.donateraja.common.filter

import com.donateraja.common.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

@Component
class CommonRequestFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {

    companion object {
        private const val TRANSACTION_ID = "transaction_id"
        private const val USER_ID = "user_id"
        private const val AUTH_PATH_PREFIX = "/auth/"
    }

    private val logger = LoggerFactory.getLogger(CommonRequestFilter::class.java)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val requestUri = request.requestURI
        // Generate unique transaction ID
        val transactionId = UUID.randomUUID().toString()
        // Extract userId from JWT or set as anonymous
        val userId = extractUserIdFromRequest(request)
        MDC.put(TRANSACTION_ID, transactionId)
        MDC.put(USER_ID, userId)
        // Exclude authentication and Swagger-related requests from logging
        if (requestUri.startsWith(AUTH_PATH_PREFIX) ||
            requestUri.startsWith("/swagger") ||
            requestUri.startsWith("/v3/api-docs") ||
            requestUri.startsWith("/webjars/swagger-ui") ||
            requestUri.startsWith("/swagger-resources")
        ) {
            filterChain.doFilter(request, response)
            return
        }

        val requestStartTime = System.currentTimeMillis()
        var statusCode: Int? = null

        try {
            filterChain.doFilter(request, response)
            statusCode = response.status
        } catch (ex: Exception) {
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            throw ex
        } finally {
            val requestDuration = System.currentTimeMillis() - requestStartTime
            logRequestDetails(request, statusCode, requestDuration)
            MDC.clear()
        }
    }

    private fun extractUserIdFromRequest(request: HttpServletRequest): String {
        // Check for Bearer token in Authorization header
        val authHeader = request.getHeader("Authorization")
        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)
            try {
                jwtUtil.extractUsername(token)
            } catch (e: Exception) {
                "anonymous"
            }
        } else {
            "anonymous"
        }
    }

    private fun logRequestDetails(request: HttpServletRequest, statusCode: Int?, duration: Long) {
        val method = request.method
        val uri = request.requestURI
        val query = request.queryString?.let { "?$it" } ?: ""
        val fullUrl = "$uri$query"

        logger.info(
            "Request Details: Method: $method, URL: $fullUrl, Status: ${statusCode ?: "UNKNOWN"}, " +
                "Duration: ${duration}ms, Transaction ID: ${MDC.get(TRANSACTION_ID)}, " +
                "User ID: ${MDC.get(USER_ID)}"
        )
    }
}
