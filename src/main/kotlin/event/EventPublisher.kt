package event

import org.springframework.messaging.Message

interface EventPublisher {

    fun <T> publish(message: Message<T>)

}