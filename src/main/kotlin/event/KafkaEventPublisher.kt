package event

import org.springframework.kafka.core.KafkaTemplate
import java.io.Serializable
import java.util.concurrent.CompletableFuture


class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, BaseEvent<*>>
): EventPublisher {

    override fun <T : Serializable> publish(
        destination: String,
        key: String,
        event: BaseEvent<T>
    ): CompletableFuture<BaseEvent<T>>
        = kafkaTemplate.send(destination, key, event)
            .thenApply { event }
}