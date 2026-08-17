package exception.handler

import exception.Error
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.context.request.WebRequest
import java.util.StringJoiner

@Component
class MethodArgumentNotValidErrorHandler : ErrorHandler {

    override fun supports(t: Throwable) =
        t is MethodArgumentNotValidException

    override fun getStatus() = HttpStatus.BAD_REQUEST

    override fun handle(t: Throwable, request: WebRequest): Error {
        val detailsJoiner = StringJoiner(", ")
        detailsJoiner.add(t.message)

        t as MethodArgumentNotValidException

        t.bindingResult.allErrors.forEach {
            detailsJoiner.add(it.defaultMessage)
        }

        return Error(detailsJoiner.toString(), request.getDescription(false))
    }
}