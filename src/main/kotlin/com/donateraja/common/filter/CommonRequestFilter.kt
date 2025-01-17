package com.donateraja.common.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class CommonRequestFilter : OncePerRequestFilter() {

    companion object {
        private const val TRANSACTION_ID = "transaction_id"
        private const val USER_ID = "user_id"
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val transactionId = UUID.randomUUID().toString()
        val userId = extractUserIdFromBearerToken(request.getHeader(AUTHORIZATION_HEADER))

        MDC.put(TRANSACTION_ID, transactionId)
        MDC.put(USER_ID, userId ?: "anonymous")

        val requestStartTime = System.currentTimeMillis()
        var statusCode: Int? = null

        try {
            // Process the request
            filterChain.doFilter(request, response)
            statusCode = response.status
        } catch (ex: Exception) {
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR // Default to 500
            throw ex
        } finally {
            val requestDuration = System.currentTimeMillis() - requestStartTime
            logRequestDetails(request, statusCode, requestDuration)
            MDC.clear()
        }
    }

    /**
     * Logs request details including method, URI, response status, and duration.
     *
     * @param request The HTTP servlet request.
     * @param statusCode The HTTP response status code.
     * @param duration The time taken to process the request (in ms).
     */
    private fun logRequestDetails(request: HttpServletRequest, statusCode: Int?, duration: Long) {
        val method = request.method
        val uri = request.requestURI
        val query = request.queryString?.let { "?$it" } ?: ""
        val fullUrl = "$uri$query"

        logger.info {
            """
            Request Details:
            - Method: $method
            - URL: $fullUrl
            - Status: ${statusCode ?: "UNKNOWN"}
            - Duration: ${duration}ms
            - Transaction ID: ${MDC.get(TRANSACTION_ID)}
            - User ID: ${MDC.get(USER_ID)}
            """.trimIndent()
        }
    }

    /**
     * Extracts the user ID from the Bearer token in the Authorization header.
     *
     * @param authorizationHeader The Authorization header.
     * @return The user ID if available, otherwise null.
     */
    private fun extractUserIdFromBearerToken(authorizationHeader: String?): String? {
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            return null
        }

        val token = authorizationHeader.substring(BEARER_PREFIX.length)
        return try {
            // Replace with JWT parsing logic to extract user information
            parseTokenToExtractUsername(token)
        } catch (ex: Exception) {
            logger.warn("Failed to parse Bearer token: ${ex.message}")
            null
        }
    }

    /**
     * Simulates token parsing. Replace with actual JWT library (e.g., io.jsonwebtoken).
     *
     * @param token The JWT token.
     * @return Extracted username.
     */
    private fun parseTokenToExtractUsername(token: String): String {
        // Example parsing logic; replace with actual JWT library logic.
        return "dummyUser" // Extracted from token (e.g., via claims)
    }
}
