package org.bagirov.postoffice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PostOfficeApplication

fun main(args: Array<String>) {
    runApplication<PostOfficeApplication>(*args)
}
