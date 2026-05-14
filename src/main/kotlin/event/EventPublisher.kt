package event

import java.io.Serializable
import java.util.concurrent.CompletableFuture

interface EventPublisher {

    fun <T : Serializable> publish(
        destination: String,
        key: String,
        event: BaseEvent<T>,
    ): CompletableFuture<BaseEvent<T>>

}