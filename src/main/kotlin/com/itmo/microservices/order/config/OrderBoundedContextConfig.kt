package com.itmo.microservices.order.config

import com.itmo.microservices.order.api.OrderAggregate
import com.itmo.microservices.order.logic.OrderAggregateState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import java.util.*

@Configuration
class OrderBoundedContextConfig {
    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun orderEsService(): EventSourcingService<UUID, OrderAggregate, OrderAggregateState> =
        eventSourcingServiceFactory.create()
}

