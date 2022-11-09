package br.poc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EntityScan
@ComponentScan
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
