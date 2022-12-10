package com.itmo.microservices.order.model

import java.util.*
import java.util.stream.Collectors


data class AppUser(
    val id: UUID,
    val userRole: UserRole,
)

enum class UserRole {
    USER,
    ADMIN;

    val precedingAndCurrent: List<UserRole>
        get() = Arrays.stream(values())
            .filter { role -> role <= this }
            .collect(Collectors.toList())
}