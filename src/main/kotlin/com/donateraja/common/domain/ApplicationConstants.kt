package com.donateraja.common.domain

enum class ApplicationConstants(
    val text: String
)  {
    TRANSACTION_ID("transaction.id"),
    USER_ID("user.id"),
    REQUEST_URL("url.full"),
    REQUEST_METHOD("http.request.method"),
    REQUEST_DURATION("event.duration"),
    RESPONSE_STATUS_CODE("http.response.status_code");
    companion object {
        // Helper method to fetch enum by key
        fun fromKey(key: String): ApplicationConstants? {
            return values().find { it.text == key }
        }
    }
}