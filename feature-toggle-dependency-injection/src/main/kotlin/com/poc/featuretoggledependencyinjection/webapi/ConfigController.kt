package com.poc.featuretoggledependencyinjection.webapi

import com.configcat.ConfigCatClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/config")
class ConfigController(
    private val configCatClient: ConfigCatClient
) {
    @GetMapping
    fun get(): ResponseEntity<String> {

        val featureValue = configCatClient
            .getValue(
                Boolean::class.java,
                "isAwesomeFeatureEnabled",
                false
            ).toString()

        return ResponseEntity.ok(featureValue)
    }
}
