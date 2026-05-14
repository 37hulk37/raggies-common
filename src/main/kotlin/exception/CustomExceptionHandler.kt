package exception

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.StringJoiner

@ControllerAdvice
class CustomExceptionHandler(
    private val objectMapper: ObjectMapper
) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Throwable::class)
    fun handleError(
        t: Throwable,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        logger.error("Error when doing operation", t)

        return handleExceptionInternal(
            RuntimeException(t),
            objectMapper.writeValueAsBytes(createError(t, request)),
            headers,
            getStatus(t),
            request
        )
    }

    private fun getStatus(t: Throwable): HttpStatusCode
        = when (t) {
            is AccessDeniedException -> HttpStatus.FORBIDDEN

            else -> HttpStatus.BAD_REQUEST
        }

    private fun createError(
        t: Throwable,
        request: WebRequest
    ): Error {
        val detailsJoiner = StringJoiner(", ")
        detailsJoiner.add(t.message)

        if (t is MethodArgumentNotValidException) {
            t.bindingResult.allErrors
                .forEach {
                    detailsJoiner.add(it.defaultMessage)
                }

        }

        if (t is MethodArgumentNotValidException) {
            t.bindingResult.allErrors
                .forEach {
                    detailsJoiner.add(it.defaultMessage)
                }

        }

        return Error(detailsJoiner.toString(), request.getDescription(false))
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>?
        = handleError(ex, request)

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = handleExceptionInternal(
        ex,
        handleError(ex, request),
        headers,
        HttpStatus.BAD_REQUEST,
        request
    )
}