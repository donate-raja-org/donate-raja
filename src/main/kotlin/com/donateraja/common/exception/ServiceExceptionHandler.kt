package com.donateraja.common.exception

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.ObjectError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.Objects
import java.util.function.Consumer
import javax.security.sasl.AuthenticationException

@ControllerAdvice
class ServiceExceptionHandler {

    private val logger = KotlinLogging.logger {}

    // Handle ServiceException (custom exception)
    @ExceptionHandler(value = [ServiceException::class])
    fun handleServiceException(serviceException: ServiceException): ResponseEntity<Any> {
        logger.error(serviceException.error.toString())
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(exception: Exception): ResponseEntity<Any> {
        logger.error(exception.message)
        if (Objects.nonNull(exception.stackTrace)) {
            for (element in exception.stackTrace) {
                if (Objects.nonNull(element)) {
                    logger.error(element.toString())
                }
            }
        }
        val serviceException = ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    @ExceptionHandler(value = [HttpMediaTypeNotSupportedException::class])
    fun handleHttpMediaTypeNotSupportedException(exception: HttpMediaTypeNotSupportedException): ResponseEntity<Any> {
        logger.error(exception.message)
        val serviceException = ServiceException(HttpStatus.BAD_REQUEST, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    @ExceptionHandler(value = [HttpRequestMethodNotSupportedException::class])
    fun handleHttpRequestMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ResponseEntity<Any> {
        logger.error(exception.message)
        val serviceException = ServiceException(HttpStatus.BAD_REQUEST, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handleConstraintViolationException(exception: ConstraintViolationException): ResponseEntity<Any> {
        logger.error("Constraint violation: ${exception.message}")
        val fieldErrors: MutableList<String> = ArrayList()
        exception.constraintViolations.forEach(
            Consumer { error: ConstraintViolation<*> ->
                fieldErrors.add(error.message)
            }
        )
        val serviceException = ServiceException(HttpStatus.BAD_REQUEST, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    @ExceptionHandler(value = [MethodArgumentTypeMismatchException::class])
    fun handleMethodArgumentTypeMismatchException(exception: MethodArgumentTypeMismatchException): ResponseEntity<Any> {
        logger.error("Argument type mismatch: ${exception.message}")
        val serviceException = ServiceException(HttpStatus.BAD_REQUEST, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<Any> {
        val fieldErrors: MutableList<String?> = ArrayList()
        exception.bindingResult.allErrors.forEach(Consumer { error: ObjectError -> fieldErrors.add(error.defaultMessage) })
        logger.error(exception.message)
        val serviceException = ServiceException(HttpStatus.BAD_REQUEST, fieldErrors.toString())
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handleHttpMessageNotReadableException(exception: HttpMessageNotReadableException): ResponseEntity<Any> {
        logger.error(exception.message)
        val serviceException = ServiceException(HttpStatus.BAD_REQUEST, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    @ExceptionHandler(value = [MissingRequestHeaderException::class])
    fun handleMissingRequestHeaderException(exception: MissingRequestHeaderException): ResponseEntity<Any> {
        logger.error(exception.message)
        val serviceException = ServiceException(HttpStatus.FORBIDDEN, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    @ExceptionHandler(value = [AccessDeniedException::class])
    fun handleAccessDeniedException(exception: AccessDeniedException): ResponseEntity<Any> {
        logger.error("Access Denied: ${exception.message}")
        val serviceException = ServiceException(HttpStatus.FORBIDDEN, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    @ExceptionHandler(value = [AuthenticationException::class])
    fun handleAuthenticationException(exception: AuthenticationException): ResponseEntity<Any> {
        logger.error("Authentication failed: ${exception.message}")
        return ResponseEntity(mapOf("error" to "Unauthorized"), HttpStatus.UNAUTHORIZED)
    }
}
