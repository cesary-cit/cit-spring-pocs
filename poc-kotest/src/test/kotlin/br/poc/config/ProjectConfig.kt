package io.kotlintest.provided

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.spring.SpringAutowireConstructorExtension
import io.kotest.extensions.spring.SpringExtension

object ProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(SpringExtension, SpringAutowireConstructorExtension)
    override val parallelism = 1
}
