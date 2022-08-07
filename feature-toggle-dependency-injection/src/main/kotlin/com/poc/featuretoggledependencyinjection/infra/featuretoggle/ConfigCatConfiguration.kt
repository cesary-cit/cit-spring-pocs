package com.poc.featuretoggledependencyinjection.infra.featuretoggle

import com.configcat.ConfigCatClient
import com.configcat.LogLevel
import com.configcat.PollingModes
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class ConfigCatConfiguration(
    @Value("\${config-cat.sdk-key}")
    val sdkKey: String,

    @Lazy
    private val rabbitListenerSubscriberCoordinator: RabbitListenerSubscriberCoordinator,
    @Lazy
    private val sqsListenerSubscriberCoordinator: SqsListenerSubscriberCoordinator
) {
    @Bean
    fun configCatClient(): ConfigCatClient {
        return ConfigCatClient
            .newBuilder()
            .mode(PollingModes.autoPoll(10) {
                rabbitListenerSubscriberCoordinator.notifyFeatureFlagChange()
                sqsListenerSubscriberCoordinator.notifyFeatureFlagChange()
            })
            .logLevel(LogLevel.INFO)
            .build(sdkKey)
    }
}
