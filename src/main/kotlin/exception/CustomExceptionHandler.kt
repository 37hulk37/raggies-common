package exception

import com.fasterxml.jackson.databind.ObjectMapper
import exception.handler.DefaultErrorHandler
import exception.handler.ErrorHandler
import org.springframework.http.*
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler(
    private val handlers: List<ErrorHandler>,
    private val defaultErrorHandler: DefaultErrorHandler,
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

        val handler = getHandler(t)
        return handleExceptionInternal(
            RuntimeException(t),
            objectMapper.writeValueAsBytes(handler.handle(t, request)),
            headers,
            handler.getStatus(),
            request
        )
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

    private fun getHandler(t: Throwable): ErrorHandler =
        handlers.firstOrNull { it !== defaultErrorHandler && it.supports(t) }
            ?: defaultErrorHandler

}