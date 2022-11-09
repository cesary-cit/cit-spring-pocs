package br.poc.domain

class InvalidRateException(rate: Rate) : RuntimeException(
    "Rate must be between 1 and 5. Restaurant: ${rate.restaurant} rate: ${rate.value}"
)