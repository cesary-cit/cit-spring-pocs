package br.poc.infrastructure.db

import br.poc.domain.Rate
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql

@DataJpaTest
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:db/rate_table_insert.sql"])
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = ["classpath:db/rate_table_truncate.sql"])
class RateRepositoryTest(val rateRepository: DbRateRepository): FunSpec({
    test("save a restaurant rate") {
        val aRandomRate = Rate("TEST REST", 1)
        rateRepository.save(aRandomRate)

        val persistedItems = rateRepository.getByRestaurant("TEST REST")

        persistedItems.size shouldBe 1
        with(persistedItems.first()) {
            restaurant shouldBe aRandomRate.restaurant
            value shouldBe aRandomRate.value
        }
    }

    test("get restaurants by name") {
        val persistedItems = rateRepository.getByRestaurant("TEST")

        persistedItems.size shouldBe 3
    }
})

