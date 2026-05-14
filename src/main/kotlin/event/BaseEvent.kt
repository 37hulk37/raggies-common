package event

import java.io.Serializable

abstract class BaseEvent<T: Serializable>(
    val source: T,
    val operation: EventOperation
): Serializable