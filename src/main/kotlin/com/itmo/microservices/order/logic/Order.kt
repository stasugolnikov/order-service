package com.itmo.microservices.order.logic

import com.itmo.microservices.order.api.*
import ru.quipy.domain.AggregateState
import java.util.*

class Order : AggregateState<UUID, OrderAggregate> {
    private lateinit var orderId: UUID

    override fun getId() = orderId

    fun createOrder(): OrderCreatedEvent {
        TODO()
    }

    fun addItemToOrder(orderId: UUID, itemId: UUID, amount: Int): ItemAddedToOrderEvent {
        TODO()
    }

    fun bookOrder(orderId: UUID): OrderBookedEvent {
        TODO()
    }

    fun updateDeliverySlot(orderId: UUID, slotInSec: Int): OrderDeliverySlotUpdatedEvent {
        TODO()
    }
}