package com.itmo.microservices.order.security

import com.itmo.microservices.order.model.user.AppUser
import com.itmo.microservices.order.model.user.UserRole

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*


class AuthToken(val id: UUID, val role: UserRole) {

    fun createAuthentication(): Authentication {
        val authorities: List<GrantedAuthority> = role.getPrecedingAndCurrent().stream()
            .map { role -> SimpleGrantedAuthority(ROLE_PREFIX + role.name) }.toList()

        return object : AbstractAuthenticationToken(authorities) {

            override fun getCredentials(): UUID {
                return id
            }

            @Synchronized
            override fun getPrincipal(): AppUser {
                return AppUser(id, role)
            }

            override fun isAuthenticated(): Boolean {
                return true
            }
        }
    }

    companion object {
        private const val ROLE_PREFIX = "ROLE_"
    }
}
