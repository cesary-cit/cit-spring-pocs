package br.poc.domain

class Rate(
    val restaurant: String,
    val value: Int
) {
    init {
        if (!(1..5).contains(value)) {
            throw InvalidRateException(this)
        }
    }
}
