package br.poc.infrastructure.message.config

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.utility.DockerImageName
import java.time.Duration

private val container = RabbitMQContainer(DockerImageName.parse("rabbitmq:3.8-management-alpine"))
    .withExposedPorts(5672)
    .withStartupTimeout(Duration.ofSeconds(60))
    .withStartupAttempts(2)

class RabbitBrokerContainer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        if (!container.isRunning) {
            container
                 //rate dlq queue
                .withExchange("rate-exchange-dlq", "direct")
                .withQueue("rate-queue-dlq", false, true, emptyMap())
                .withBinding("rate-exchange-dlq", "rate-queue-dlq")
                //rate queue
                .withExchange("rate-exchange", "direct")
                .withQueue("rate-queue", false, true, mapOf(
                    "x-message-ttl" to 3000,
                    "x-dead-letter-exchange"   to "rate-exchange-dlq",
                    "x-dead-letter-routing-key" to "rate-dlq-routing-key"
                 ))
                .withBinding("rate-exchange", "rate-queue")
                .start()

            TestPropertyValues.of(
                "spring.rabbitmq.host=" + container.host,
                "spring.rabbitmq.port=" + container.getMappedPort(5672),
                "spring.rabbitmq.listener.simple.retry.enabled=true",
                "spring.rabbitmq.listener.simple.retry.max-attempts=2",
                "spring.rabbitmq.listener.simple.retry.initial-interval=1s",
                "spring.rabbitmq.listener.simple.retry.max-interval=1s"
            ).applyTo(applicationContext)
        }
    }
}
