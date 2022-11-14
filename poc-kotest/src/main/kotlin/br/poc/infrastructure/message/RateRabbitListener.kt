package br.poc.infrastructure.message

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RateRabbitListener() {
    private val logger: Logger = LoggerFactory.getLogger(RateRabbitListener::class.java)

    @RabbitListener(
        id = "rate-listener",
        queues = ["rate-queue"],
        autoStartup = "false"
    )
    fun consume(message: Message) {
        logger.info("consumed: ${String(message.body)}")
    }
}
