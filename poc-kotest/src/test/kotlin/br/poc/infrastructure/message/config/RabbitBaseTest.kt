package br.poc.infrastructure.message.config

import io.kotest.core.spec.style.StringSpec
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [MessagingIntegrationTestsConfiguration::class], initializers = [RabbitBrokerContainer::class])
abstract class RabbitBaseTest (body: StringSpec.() -> Unit = {}) : StringSpec(body) {
    @Autowired
    private lateinit var rabbitListenerEndpointRegistry: RabbitListenerEndpointRegistry

    @Autowired
    private lateinit var admin: RabbitAdmin

    fun isQueueEmpty(queueName: String) = admin.getQueueProperties(queueName)["QUEUE_MESSAGE_COUNT"] == 0

    fun startConsumer(listenerId: String) {
        val listener = rabbitListenerEndpointRegistry.getListenerContainer(listenerId)
        if (!listener.isRunning) listener.start()
    }

    fun stopConsumer(listenerId: String) {
        val listener = rabbitListenerEndpointRegistry.getListenerContainer(listenerId)
        if (listener.isRunning) listener.stop()
    }

    fun purgeQueues(queueNames: List<String>) {
        queueNames.onEach {
            admin.purgeQueue(it, false)
        }
    }
}

@ComponentScan
@ImportAutoConfiguration(RabbitAutoConfiguration::class)
private class MessagingIntegrationTestsConfiguration
