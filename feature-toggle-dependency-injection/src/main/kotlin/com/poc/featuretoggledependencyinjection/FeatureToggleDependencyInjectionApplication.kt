package com.poc.featuretoggledependencyinjection

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FeatureToggleDependencyInjectionApplication

fun main(args: Array<String>) {
	runApplication<FeatureToggleDependencyInjectionApplication>(*args)
}
