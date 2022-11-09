package br.poc.infrastructure.db

import br.poc.domain.Rate
import br.poc.domain.RateRepository
import org.springframework.stereotype.Component

@Component
class DbRateRepository(
    private val jpaRateRepository: JpaRateRepository
) : RateRepository {
    override fun save(rate: Rate) {
        val entity = RateEntity.fromDomain(rate)
        jpaRateRepository.save(entity)
    }

    override fun getByRestaurant(restaurantName: String) =
        jpaRateRepository
            .getByRestaurantName(restaurantName)
            .map { RateEntity.fromEntity(it) }


    override fun getById(id: String): Rate? {
        TODO("Not yet implemented")
    }
}