package org.d7z.light.db.spring.boot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
open class Application {

    open fun main(args: Array<String>) {
        SpringApplication.run(Application::class.java, *args)
    }
}
