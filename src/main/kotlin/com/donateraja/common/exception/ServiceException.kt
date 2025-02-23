package com.donateraja.common.exception

import com.donateraja.common.ApplicationConstants
import org.slf4j.MDC
import org.springframework.http.HttpStatus

class ServiceException(val httpStatus: HttpStatus, description: String?) : Exception(httpStatus.name) {
    val error: ServiceError = ServiceError(
        httpStatus.value(),
        httpStatus.name,
        description,
        MDC.get(ApplicationConstants.TRANSACTION_ID.text)
    )
}
