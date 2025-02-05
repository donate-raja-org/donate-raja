package com.donateraja.common.exception

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.*
import java.util.function.Consumer

@ControllerAdvice
class ServiceExceptionHandler {

    private val logger = KotlinLogging.logger {}

    // Handle ServiceException (custom exception)
    @ExceptionHandler(value=[ServiceException::class])
    fun handleServiceException(serviceException: ServiceException): ResponseEntity<Any> {
        logger.error("Error occurred: ${serviceException.error}")
        // Log the transaction ID for tracing
        logger.info("Transaction ID: ${serviceException.error.transactionId}")
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }


    @ExceptionHandler(value= [Exception::class])
    fun handleException(exception: Exception): ResponseEntity<Any> {
        logger.error(exception.message)
        if (Objects.nonNull(exception.stackTrace)){
            for (element in exception.stackTrace){
                if (Objects.nonNull(element)){
                    logger.error(element.toString())
                }
            }
        }
        val serviceException = ServiceException(HttpStatus.INTERNAL_SERVER_ERROR,exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }
    // Handle MethodArgumentNotValidException (Invalid request body)
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<Any> {
        logger.error("Validation failed: ${exception.message}")
        val serviceException = ServiceException(HttpStatus.BAD_REQUEST, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    // Handle MethodArgumentTypeMismatchException (Argument type mismatch)
    @ExceptionHandler(value = [MethodArgumentTypeMismatchException::class])
    fun handleMethodArgumentTypeMismatchException(exception: MethodArgumentTypeMismatchException): ResponseEntity<Any> {
        logger.error("Argument type mismatch: ${exception.message}")
        val serviceException = ServiceException(HttpStatus.BAD_REQUEST, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    // Handle HttpMethodNotSupportedException (Unsupported HTTP method)
    @ExceptionHandler(value = [HttpRequestMethodNotSupportedException::class])
    fun handleHttpMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ResponseEntity<Any> {
        logger.error("HTTP method not supported: ${exception.message}")
        val serviceException = ServiceException(HttpStatus.METHOD_NOT_ALLOWED, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    // Handle HttpMessageNotReadableException (Malformed request body)
    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handleHttpMessageNotReadableException(exception: HttpMessageNotReadableException): ResponseEntity<Any> {
        logger.error("Malformed request body: ${exception.message}")
        val serviceException = ServiceException(HttpStatus.BAD_REQUEST, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    // Handle ConstraintViolationException (Constraint violations)
    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handleConstraintViolationException(exception: ConstraintViolationException): ResponseEntity<Any> {
        logger.error("Constraint violation: ${exception.message}")
        val fieldErrors: MutableList<String> = ArrayList()
        exception.constraintViolations.forEach(
            Consumer {
                error : ConstraintViolation<*> ->fieldErrors.add(error.message)
            }
        )
        val serviceException = ServiceException(HttpStatus.BAD_REQUEST, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

    @ExceptionHandler(value = [AccessDeniedException::class])
    fun handleAccessDeniedException(exception: AccessDeniedException): ResponseEntity<Any> {
        logger.error("Access Denied: ${exception.message}")
        val serviceException = ServiceException(HttpStatus.FORBIDDEN, exception.message)
        return ResponseEntity(serviceException.error, serviceException.httpStatus)
    }

}


