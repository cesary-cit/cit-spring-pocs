package com.poc.featuretoggledependencyinjection.infra.sqs

import com.poc.featuretoggledependencyinjection.core.domain.featuretoggle.FeatureToggleKey
import com.poc.featuretoggledependencyinjection.core.domain.message.ReleaseSubscriberMessageListener
import com.poc.featuretoggledependencyinjection.infra.rabbitmq.OrderListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class ItemListener {
//    override val featureKey = FeatureToggleKey.RT_POC_OMS_SQS
//    override val listenerId = "itemListener"

    private val logger: Logger = LoggerFactory.getLogger(ItemListener::class.java)

    @SqsListener(value = ["testObjectQueue"], deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun receiveMessage(
        message: String,
        @Header("SenderId") senderId: String?
    ) {
        logger.info("message received {} {}", senderId, message)
    }

//    override fun consume(message: Message) {
//        TODO("Not yet implemented")
//    }
}