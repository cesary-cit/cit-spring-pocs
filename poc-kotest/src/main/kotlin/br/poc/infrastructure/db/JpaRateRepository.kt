package br.poc.infrastructure.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaRateRepository : JpaRepository<RateEntity, String> {
    fun getByRestaurantName(restaurantName: String): List<RateEntity>
}
