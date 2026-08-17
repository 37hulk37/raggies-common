package exception

open class ApplicationException(
    message: String,
    cause: Throwable? = null
): RuntimeException(message, cause) {

    constructor(message: String):
            this(message, null)

}