package exception.handlers

import exception.Error
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@Component
class AccessDeniedErrorHandler : ErrorHandler {

    override fun supports(t: Throwable) =
        t is AccessDeniedException

    override fun getStatus() = HttpStatus.FORBIDDEN

    override fun handle(
        t: Throwable,
        request: WebRequest
    ) = Error(
        t.message ?: "Access denied",
        request.getDescription(false)
    )
}