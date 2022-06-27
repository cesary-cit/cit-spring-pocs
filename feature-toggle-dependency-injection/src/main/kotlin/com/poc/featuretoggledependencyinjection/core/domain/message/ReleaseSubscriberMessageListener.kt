package com.poc.featuretoggledependencyinjection.core.domain.message

import com.poc.featuretoggledependencyinjection.core.domain.featuretoggle.FeatureToggleKey

interface ReleaseSubscriberMessageListener : MessageListener {
    val featureKey: FeatureToggleKey
    val listenerId: String
}
