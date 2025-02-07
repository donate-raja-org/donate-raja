package com.donateraja.controller

import com.donateraja.annotation.PublicAccess
import com.donateraja.annotation.RequireAdmin
import com.donateraja.annotation.RequireUser
import com.donateraja.annotation.RequireUserOrAdmin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TestController {

    @GetMapping("/public")
    @PublicAccess
    fun publicEndpoint(): String = "Public API: No Authentication Required"

    @GetMapping("/user")
    @RequireUser
    fun userEndpoint(): String = "User API: Accessible only by Users"

    @GetMapping("/admin")
    @RequireAdmin
    fun adminEndpoint(): String = "Admin API: Accessible only by Admins"

    @GetMapping("/user-or-admin")
    @RequireUserOrAdmin
    fun userOrAdminEndpoint(): String = "User or Admin API: Accessible by both Users and Admins"
}
