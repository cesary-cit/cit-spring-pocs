package com.poc.featuretoggledependencyinjection.infra.featuretoggle

import com.configcat.ConfigCatClient
import com.poc.featuretoggledependencyinjection.core.domain.message.ReleaseSubscriberMessageListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry
import org.springframework.stereotype.Component

@Component
class RabbitListenerSubscriberCoordinator(
    private val rabbitListenerEndpointRegistry: RabbitListenerEndpointRegistry,
    private val releasableListeners: List<ReleaseSubscriberMessageListener>,
    private val configCatClient: ConfigCatClient
) {
    private val logger: Logger = LoggerFactory.getLogger(RabbitListenerSubscriberCoordinator::class.java)

    fun notifyFeatureFlagChange() {
        releasableListeners.onEach {
            val isEnabled = configCatClient.getValue(
                Boolean::class.java,
                it.featureKey.toString(),
                it.featureKey.defaultValue
            )

            if (isEnabled)
                startListener(it.listenerId)
            else
                stopListener(it.listenerId)
        }
    }

    fun startListener(listenerId: String) {
        val listener = rabbitListenerEndpointRegistry.getListenerContainer(listenerId)

        if (!listener.isRunning) {
            logger.info("Starting listener $listenerId")

            listener.start()
            Thread.sleep(1_000)
        }
    }

    fun stopListener(listenerId: String) {
        val listener = rabbitListenerEndpointRegistry.getListenerContainer(listenerId)

        if (listener.isRunning) {
            logger.info("Stopping listener $listenerId")

            listener.stop()
            Thread.sleep(1_000)
        }
    }
}
