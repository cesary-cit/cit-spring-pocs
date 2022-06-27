package com.poc.featuretoggledependencyinjection.core.domain.message

import org.springframework.amqp.core.Message

interface MessageListener {
    fun consume(message: Message)
}
