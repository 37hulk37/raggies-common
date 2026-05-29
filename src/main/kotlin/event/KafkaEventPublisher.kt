package event

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.Message


class KafkaEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, BaseEvent<*>>
): EventPublisher {

    companion object {
        val log = LoggerFactory.getLogger(KafkaEventPublisher::class.java)!!
    }

    override fun <T> publish(message: Message<T>) {
        kafkaTemplate.send(message)
            .exceptionally { t ->
                log.error("Error while publishing ${message.payload}  to ${message.headers["destination"]} with ${message.headers["key"]}", t)

                null
            }
    }

}