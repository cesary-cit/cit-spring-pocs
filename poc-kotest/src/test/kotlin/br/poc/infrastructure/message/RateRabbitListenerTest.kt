package br.poc.infrastructure.message

import br.poc.infrastructure.message.config.RabbitBaseTest
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Import

@Import(RateRabbitListener::class)
class RateRabbitListenerTest(
    private val template: RabbitTemplate,
    private val listener: RateRabbitListener
) : RabbitBaseTest() {
    override suspend fun afterContainer(testCase: TestCase, result: TestResult) {
        purgeQueues(listOf("rate-queue-dlq", "rate-queue"))
        stopConsumer("rate-listener")
    }

    init {
        "should start test" {
            startConsumer("rate-listener")
            template.sendAndReceive("rate-exchange", "rate-routing-key", Message("Integration Test".toByteArray()))

            isQueueEmpty("rate-queue") shouldBe true
            isQueueEmpty("rate-queue-dlq") shouldBe true
        }
    }
}
