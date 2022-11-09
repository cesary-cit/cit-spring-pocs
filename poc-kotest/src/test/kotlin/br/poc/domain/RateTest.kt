package br.poc.domain

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class RateTest : BehaviorSpec({
    given("a negative rate") {
        val rate = -1
        And("a random restaurant") {
            `when`("create a rate") {
                val error = shouldThrowExactly<InvalidRateException> {
                    Rate("Restaurant test", rate)
                }
                then("a validation error occurs") {
                    error.message shouldBe "Rate must be between 1 and 5. Restaurant: Restaurant test rate: -1"
                }
            }
        }
    }

    given("a rate greater than 5") {
        val invalidRateRange = 10
        And("a random restaurant") {
            val randomRestaurant = "Restaurant test"
            `when`("try to create a range") {
                val error = shouldThrowExactly<InvalidRateException> {
                    Rate(randomRestaurant, invalidRateRange)
                }
                Then("a validation error occurs") {
                    error.message shouldBe "Rate must be between 1 and 5. Restaurant: Restaurant test rate: 10"
                }
            }
        }
    }
})
