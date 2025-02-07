package com.donateraja.common.network

import com.donateraja.common.exception.ServiceException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate

@Component
class HttpServiceProvider(
    @Lazy
    private val restTemplate: RestTemplate
) {

    private val logger = KotlinLogging.logger {}
    private val objectMapper = jacksonObjectMapper().apply { findAndRegisterModules() }

    @Bean
    private fun restTemplateProvider(): RestTemplate = restTemplate

    /**
     * Performs an HTTP exchange and handles errors centrally.
     *
     * @param url The target URL.
     * @param method The HTTP method (GET, POST, etc.).
     * @param requestBody The request body (optional).
     * @param responseType The response type.
     * @param headers Additional HTTP headers (optional).
     * @return ResponseEntity of the expected response type.
     * @throws ServiceException if an error occurs during the HTTP call.
     */
    @Throws(ServiceException::class)
    fun <T> exchange(
        url: String,
        method: HttpMethod,
        httpEntity: HttpEntity<Any>,
        responseType: Class<T>,
        logPayloadAvailable: Boolean = true
    ): ResponseEntity<T> = try {
        logger.info { "Making $method request to URL: $url" }
        logRequestBody(httpEntity, logPayloadAvailable)
        restTemplateProvider().exchange(url, method, httpEntity, responseType)
    } catch (exception: HttpStatusCodeException) {
        // Log and rethrow as ServiceException for 4xx/5xx errors
        val httpStatus = HttpStatus.valueOf(exception.statusCode.value())
        logger.error("Url :$url status code :${exception.statusCode} message : ${exception.responseBodyAsString} ")
        throw ServiceException(httpStatus, exception.responseBodyAsString)
    } catch (exception: Exception) {
        // Log and rethrow for generic errors
        logger.error("Unexpected error during exchange to $url")
        throw ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, exception.message)
    }

    /**
     * Logs the details of the request, including the URL, method, and a pretty-printed request body.
     */
    private fun logRequestBody(httpEntity: HttpEntity<Any>, logPayloadAvailable: Boolean) {
        if (logPayloadAvailable && httpEntity.hasBody()) {
            try {
                val prettyBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(httpEntity.body)
                logger.debug { "Request body: $prettyBody" }
            } catch (ex: Exception) {
                logger.error("unable to log the request body {}", ex.message)
            }
        }
    }
}
