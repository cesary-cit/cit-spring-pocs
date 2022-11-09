package br.poc.domain

class InvalidSummaryException : RuntimeException("Rates must be from the same restaurant.") {
}