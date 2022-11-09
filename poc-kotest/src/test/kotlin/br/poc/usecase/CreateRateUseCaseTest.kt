package br.poc.usecase

import br.poc.domain.Rate
import br.poc.domain.RateRepository
import io.kotest.core.spec.style.ShouldSpec
import io.mockk.*
import org.jeasy.random.EasyRandom

class CreateRateUseCaseTest : ShouldSpec({
    val mockRepository = mockk<RateRepository>()
    val createRateUseCase = CreateRateUseCase(mockRepository)

    context("create rate use case") {
        should("create a use case successfully") {
            every { mockRepository.save(any()) } just Runs
            val aRandomRate = EasyRandom().nextObject(Rate::class.java)
            createRateUseCase.create(aRandomRate)
            verify(exactly = 1) { mockRepository.save(aRandomRate) }
        }
    }
})
