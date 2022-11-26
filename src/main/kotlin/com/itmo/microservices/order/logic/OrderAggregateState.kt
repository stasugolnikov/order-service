package com.itmo.microservices.order.logic

import com.itmo.microservices.order.api.*
import com.itmo.microservices.order.model.OrderStatus
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class OrderAggregateState : AggregateState<UUID, OrderAggregate> {
    private lateinit var orderId: UUID
    private lateinit var userId: UUID

    private var status: OrderStatus = OrderStatus.COLLECTING
    private var timeCreated: Long = System.currentTimeMillis()
    private var orderItemsAmount: MutableMap<UUID, Int> = mutableMapOf()

    override fun getId() = orderId

    fun createOrder(auth: String): OrderCreatedEvent {
        val userId = UUID.randomUUID() // todo get user from auth
        return OrderCreatedEvent(UUID.randomUUID(), userId)
    }

    fun addItemToOrder(orderId: UUID, itemId: UUID, amount: Int): ItemAddedToOrderEvent {
        return ItemAddedToOrderEvent(orderId, itemId, amount)
    }

    fun bookOrder(orderId: UUID): OrderBookedEvent {
        return OrderBookedEvent(orderId)
    }

    @StateTransitionFunc
    fun createOrder(event: OrderCreatedEvent) {
        orderId = event.orderId
        userId = event.userId
    }

    @StateTransitionFunc
    fun addItemToOrder(event: ItemAddedToOrderEvent) {
        orderItemsAmount[event.itemId] = orderItemsAmount.getOrDefault(event.itemId, 0) + event.amount
    }

    @StateTransitionFunc
    fun bookOrder(event: OrderBookedEvent) {
        status = OrderStatus.BOOKED
    }

}