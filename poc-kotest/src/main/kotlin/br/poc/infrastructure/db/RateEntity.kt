package br.poc.infrastructure.db

import br.poc.domain.Rate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "[rate]")
class RateEntity (
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "restaurant_name")
    val restaurantName: String,

    @Column(name = "rate")
    val rate: Int,
) {
    companion object {
        fun fromDomain(rate: Rate) = RateEntity(
            UUID.randomUUID().toString(),
            rate.restaurant,
            rate.value
        )

        fun fromEntity(rate: RateEntity) = Rate(
            rate.restaurantName,
            rate.rate
        )
    }
}

