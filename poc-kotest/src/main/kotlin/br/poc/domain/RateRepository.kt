package br.poc.domain

interface RateRepository {
    fun save(rate: Rate)
    fun getByRestaurant(restaurantName: String): List<Rate>
    fun getById(id: String): Rate?
}
