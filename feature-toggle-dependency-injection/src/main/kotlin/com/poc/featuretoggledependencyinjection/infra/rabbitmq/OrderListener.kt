package com.poc.featuretoggledependencyinjection.infra.rabbitmq

import com.poc.featuretoggledependencyinjection.core.domain.featuretoggle.FeatureToggleKey
import com.poc.featuretoggledependencyinjection.core.domain.message.ReleaseSubscriberMessageListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.amqp.core.Message

@Component
class OrderListener(
) : ReleaseSubscriberMessageListener {
    override val featureKey: FeatureToggleKey = FeatureToggleKey.RT_POC_OMS
    override val listenerId: String = "orderListener"

    private val logger: Logger = LoggerFactory.getLogger(OrderListener::class.java)

    @RabbitListener(
        id = "orderListener",
        queues = ["oms-poc-order-queue"]
    )
    override fun consume(message: Message) {
        logger.info("ORDER LISTENER - ${String(message.body)}")
        Thread.sleep(10000)
    }
}
