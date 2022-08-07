package com.poc.featuretoggledependencyinjection.infra.featuretoggle

import com.configcat.ConfigCatClient
import com.poc.featuretoggledependencyinjection.core.domain.featuretoggle.FeatureToggleKey
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer
import org.springframework.stereotype.Component

@Component
class SqsListenerSubscriberCoordinator(
    private val listenerContainer: SimpleMessageListenerContainer,
    private val configCatClient: ConfigCatClient
) {
    private val logger: Logger = LoggerFactory.getLogger(RabbitListenerSubscriberCoordinator::class.java)

    fun notifyFeatureFlagChange() {
        val isEnabled = configCatClient.getValue(
            Boolean::class.java,
            FeatureToggleKey.RT_POC_OMS_SQS.toString(),
            FeatureToggleKey.RT_POC_OMS_SQS.defaultValue
        )

        if (isEnabled)
            startListener("testObjectQueue")
        else
            stopListener("testObjectQueue")

    }

    fun startListener(queue: String) {
        listenerContainer.isRunning

        if (!listenerContainer.isRunning(queue)) {
            logger.info("Starting listener for queue $queue")

            listenerContainer.start(queue)
            Thread.sleep(1_000)
        }
    }

    fun stopListener(queue: String) {
        if (listenerContainer.isRunning) {
            logger.info("Stopping listener for queue $queue")

            listenerContainer.stop(queue)
            Thread.sleep(1_000)
        }
    }
}
