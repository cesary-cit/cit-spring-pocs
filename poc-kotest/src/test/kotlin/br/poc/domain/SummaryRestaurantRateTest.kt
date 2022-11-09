package br.poc.domain

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.jeasy.random.EasyRandom
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.stream.Collectors

class SummaryRestaurantRateTest : ShouldSpec({
    context("creation validations") {
        should("get error when there is any rate with a different restaurant name") {
            val differentRates = EasyRandom().objects(Rate::class.java, 2).collect(Collectors.toList())

            val error = shouldThrowExactly<InvalidSummaryException> {
                SummaryRestaurantRate(differentRates)
            }

            error.message shouldBe "Rates must be from the same restaurant."
        }
    }

    context("Average calculation") {
        should("get average when there are many rates for the same restaurant") {
            val rates = listOf(
                Rate("Test", 3),
                Rate("Test", 3),
                Rate("Test", 3),
                Rate("Test", 3),
            )

            val summary = SummaryRestaurantRate(rates)

            summary.getAverage() shouldBe BigDecimal(3).setScale(1)
        }

        should("half up with 1 place") {
            val rates = listOf(
                Rate("Test", 1),
                Rate("Test", 3),
                Rate("Test", 2),
                Rate("Test", 3)
            )

            val summary = SummaryRestaurantRate(rates)

            summary.getAverage() shouldBe BigDecimal(2.3).setScale(1, RoundingMode.HALF_UP)
        }
    }
})