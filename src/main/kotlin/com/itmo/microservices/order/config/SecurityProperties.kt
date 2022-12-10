package com.itmo.microservices.order.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.convert.DurationUnit
import org.springframework.validation.annotation.Validated
import java.time.Duration
import java.time.temporal.ChronoUnit
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@ConfigurationProperties("security")
@Validated
@ConstructorBinding
data class SecurityProperties(
    @NotBlank
    @Size(min = 32)
    val secret: String,

    @DurationUnit(ChronoUnit.DAYS)
    val tokenTtl: Duration
)
