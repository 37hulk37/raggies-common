package event

import java.io.Serializable

abstract class BaseEvent<T>(
    val source: T,
    val operation: String
): Serializable