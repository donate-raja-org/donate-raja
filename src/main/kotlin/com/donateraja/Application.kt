package com.donateraja

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class DonaterajaApplication

fun main(args: Array<String>) {
    println("hashedPassword::${BCryptPasswordEncoder().encode("Admin@123")}")
    runApplication<DonaterajaApplication>(*args)
}
