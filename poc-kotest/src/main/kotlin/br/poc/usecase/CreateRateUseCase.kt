package br.poc.usecase

import br.poc.domain.Rate
import br.poc.domain.RateRepository
import org.springframework.stereotype.Service

@Service
class CreateRateUseCase(private val rateRepository: RateRepository) {
    fun create(rate: Rate) {
        rateRepository.save(rate)
    }
}
