package com.itmo.microservices.order.security

import com.itmo.microservices.order.config.props.SecurityProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(private val securityProperties: SecurityProperties) {

    fun readToken(token: String?): AuthToken {
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(securityProperties.secret.encodeToByteArray())
            .build()
            .parseClaimsJws(token)
            .getBody()
        val userId = UUID.fromString(
            claims.get("userId", String::class.java)
                ?: throw IllegalArgumentException("userId is null")
        )
        return AuthToken(userId)
    }
}
