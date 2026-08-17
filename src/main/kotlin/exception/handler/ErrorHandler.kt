package exception.handler

import exception.Error
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.context.request.WebRequest

interface ErrorHandler {

    fun supports(t: Throwable): Boolean

    fun getStatus(): HttpStatusCode

    fun handle(t: Throwable, request: WebRequest): Error

}