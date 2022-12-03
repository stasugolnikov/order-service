package com.itmo.microservices.order.security

import com.itmo.microservices.order.model.AppUser

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*


class AuthToken(val id: UUID) {

    fun createAuthentication(): Authentication {
        val self = this
        //TODO добавить роли
        val authorities: List<GrantedAuthority> = listOf(SimpleGrantedAuthority(ROLE_PREFIX + "name"))

        return object : AbstractAuthenticationToken(authorities) {

            override fun getCredentials(): UUID {
                return id
            }

            @Synchronized
            override fun getPrincipal(): AppUser {
                return AppUser(id)
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
