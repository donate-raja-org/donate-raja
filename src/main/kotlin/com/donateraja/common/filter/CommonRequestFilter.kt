package com.donateraja.common.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class CommonRequestFilter : OncePerRequestFilter() {

    companion object {
        private const val TRANSACTION_ID = "transaction_id"
        private const val USER_ID = "user_id"
        private const val AUTH_PATH_PREFIX = "/auth/"
    }

    private val logger = LoggerFactory.getLogger(CommonRequestFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Exclude authentication requests from being logged
        if (request.requestURI.startsWith(AUTH_PATH_PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val transactionId = UUID.randomUUID().toString()

        // Retrieve user ID from the request attribute set by JwtAuthenticationFilter
        val userId = request.getAttribute(USER_ID) as? String ?: "anonymous"

        MDC.put(TRANSACTION_ID, transactionId)
        MDC.put(USER_ID, userId)
//        MDC.put(ROLE, )

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

