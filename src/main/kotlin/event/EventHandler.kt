package event

import org.springframework.messaging.Message
import java.io.Serializable

interface EventHandler<E : Serializable> {

    fun handleEvent(event: Message<E>)
}