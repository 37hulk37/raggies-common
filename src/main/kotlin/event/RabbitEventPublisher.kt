package event

import exception.ApplicationException
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import java.io.Serializable
import java.util.concurrent.CompletableFuture

class RabbitEventPublisher(
    private val rabbitTemplate: RabbitTemplate
): EventPublisher {

    companion object {
        val log = LoggerFactory.getLogger(RabbitEventPublisher::class.java)!!
    }

    override fun <T : Serializable> publish(
        destination: String,
        key: String,
        event: BaseEvent<T>,
    ): CompletableFuture<BaseEvent<T>>
        = CompletableFuture.supplyAsync {
            rabbitTemplate.convertAndSend(destination, key, event)

            log.info("Event published: ${event::class.simpleName} to destination $destination with routing key $key")

            event
        }
        .exceptionally { t ->
            log.error("Error while publishing $event  to $destination with $key", t)

            throw ApplicationException("Error while publishing event $event", t)
        }
}
