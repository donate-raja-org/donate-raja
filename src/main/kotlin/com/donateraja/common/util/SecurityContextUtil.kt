package com.donateraja.common.util

import org.slf4j.MDC
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

object SecurityContextUtil {
    fun getUserIdFromContext(): String =
        MDC.get("user_id") ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User context not found")
}
