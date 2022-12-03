package com.itmo.microservices.order.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    private val jwtUtil: JwtUtil? = null

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {

        readTokenIfProvided(req)

        chain.doFilter(req, res)
    }

    private fun readTokenIfProvided(req: HttpServletRequest) {
        val tokenString = req.getHeader("Authorization")
        if (tokenString == null || !tokenString.startsWith("Bearer ")) return
        val token = tokenString.substring("Bearer ".length)
        val authToken = jwtUtil!!.readToken(token)
        SecurityContextHolder.getContext().authentication = authToken.createAuthentication()

    }
}
