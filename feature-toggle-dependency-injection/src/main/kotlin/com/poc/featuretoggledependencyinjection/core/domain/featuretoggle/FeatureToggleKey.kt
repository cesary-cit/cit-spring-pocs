package com.poc.featuretoggledependencyinjection.core.domain.featuretoggle

enum class FeatureToggleKey(val defaultValue: Boolean) {
    RT_POC_OMS(false),
    RT_POC_OMS_SQS(false)
}
