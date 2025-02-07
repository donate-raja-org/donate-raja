package com.donateraja

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DonaterajaApplication

fun main(args: Array<String>) {
//    println("hashedPassword::${BCryptPasswordEncoder().encode("Admin@123")}")
//
//    // Generate a 256-bit secret key for HMACSHA256
//    val keyGenerator: KeyGenerator = KeyGenerator.getInstance("HmacSHA256")
//    keyGenerator.init(256) // 256-bit key
//    val secretKey: SecretKey = keyGenerator.generateKey()
//    val base64Secret: String = Base64.getEncoder().encodeToString(secretKey.encoded)
//    println("Generated Base64 Secret: $base64Secret") // Print the Base64 encoded secret

    runApplication<DonaterajaApplication>(*args)
}
