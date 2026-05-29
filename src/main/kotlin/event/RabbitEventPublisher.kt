package event

import exception.ApplicationException
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import org.springframework.messaging.Message

class RabbitEventPublisher(
    private val rabbitTemplate: RabbitMessagingTemplate
): EventPublisher {

    companion object {
        val log = LoggerFactory.getLogger(RabbitEventPublisher::class.java)!!
    }

    override fun <T> publish(message: Message<T>) {
        val destination = message.headers.get("destination", String::class.java)
        val routingKey = message.headers.get("evenType", String::class.java)

        if (destination == null || routingKey == null) {
            throw ApplicationException("destination or routing-key not found")
        }

        rabbitTemplate.send(destination, routingKey, message)

        log.info("Event published: ${message.payload::class.simpleName} to destination $destination with routing key $routingKey")
    }
}
