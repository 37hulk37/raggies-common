package exception.handlers

import exception.Error
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@Component
class DefaultErrorHandler : ErrorHandler {

    override fun supports(t: Throwable): Boolean = true

    override fun getStatus(): HttpStatusCode = HttpStatus.BAD_REQUEST

    override fun handle(
        t: Throwable,
        request: WebRequest
    ) = Error(
        "Something went wrong",
        request.getDescription(false)
    )


}