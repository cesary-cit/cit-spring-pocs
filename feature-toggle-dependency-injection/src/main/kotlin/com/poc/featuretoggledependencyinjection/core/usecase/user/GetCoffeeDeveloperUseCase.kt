package com.poc.featuretoggledependencyinjection.core.usecase.user

import com.poc.featuretoggledependencyinjection.core.domain.developer.Developer
import com.poc.featuretoggledependencyinjection.core.domain.developer.GetDeveloperUseCase
import org.springframework.stereotype.Service

@Service
class GetCoffeeDeveloperUseCase : GetDeveloperUseCase {
    override fun getActive() : Developer {
        return Developer("Nice", "Coffee")
    }
}
