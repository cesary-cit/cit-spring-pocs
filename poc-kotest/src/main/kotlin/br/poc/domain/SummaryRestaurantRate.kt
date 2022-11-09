package br.poc.domain

import java.math.BigDecimal
import java.math.RoundingMode

class SummaryRestaurantRate(
    private val rates: Collection<Rate>
) {
    init {
        if(rates.distinctBy { it.restaurant }.count() > 1) {
            throw InvalidSummaryException()
        }
    }

    fun getAverage() = BigDecimal(rates.map { it.value }.average()).setScale(1, RoundingMode.HALF_UP)
}
