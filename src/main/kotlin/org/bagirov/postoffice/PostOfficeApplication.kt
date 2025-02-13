package org.bagirov.postoffice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["org.bagirov.postoffice.repository"])
class PostOfficeApplication

fun main(args: Array<String>) {
    runApplication<PostOfficeApplication>(*args)
}
