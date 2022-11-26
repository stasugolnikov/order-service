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
    fun getUserId() = userId

    fun getOrderItemsAmount() = orderItemsAmount

    fun getStatus() = status

    fun createOrder(auth: String): OrderCreatedEvent {
        val userId = UUID.fromString(auth) // todo get user from auth
        return OrderCreatedEvent(UUID.randomUUID(), userId)
    }

    fun addItemToOrder(orderId: UUID, itemId: UUID, amount: Int): ItemAddedToOrderEvent {
        return ItemAddedToOrderEvent(orderId, itemId, amount)
    }

    fun deleteItemFromOrder(orderId: UUID, itemId: UUID, amount: Int): ItemRemovedFromOrderEvent {
        return ItemRemovedFromOrderEvent(orderId, itemId, amount)
    }

    fun bookOrder(orderId: UUID): OrderBookedEvent {
        return OrderBookedEvent(orderId)
    }

    fun deliveryStartedOrder(orderId: UUID): OrderDeliveryStartedEvent {
        return OrderDeliveryStartedEvent(orderId)
    }

    fun deliveryFailedOrder(orderId: UUID): OrderDeliveryFailedEvent {
        return OrderDeliveryFailedEvent(orderId)
    }

    fun deliveryCompletedOrder(orderId: UUID): OrderDeliveryCompletedEvent {
        return OrderDeliveryCompletedEvent(orderId)
    }

    fun discardOrder(orderId: UUID): OrderBookingCanceledEvent {
        return OrderBookingCanceledEvent(orderId)
    }

    fun expiredOrder(orderId: UUID): OrderExpiredEvent {
        return OrderExpiredEvent(orderId)
    }

    fun orderPaid(orderId: UUID): OrderPaidEvent {
        return OrderPaidEvent(orderId)
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

    @StateTransitionFunc
    fun deliveryStartedOrder(event: OrderDeliveryStartedEvent) {
        status = OrderStatus.SHIPPING
    }

    @StateTransitionFunc
    fun deliveryFailedOrder(event: OrderDeliveryFailedEvent) {
        status = OrderStatus.REFUND
    }

    @StateTransitionFunc
    fun deliveryCompletedOrder(event: OrderDeliveryCompletedEvent) {
        status = OrderStatus.COMPLETED
    }

    @StateTransitionFunc
    fun discardOrder(event: OrderBookingCanceledEvent) {
        status = OrderStatus.DISCARD
    }

    @StateTransitionFunc
    fun removeItem(event: ItemRemovedFromOrderEvent) {
        val bill =
            orderItemsAmount[event.itemId] ?: error("Item with id=${event.itemId} not found in order ${event.orderId}")
        require(event.itemCount <= bill)
        if (event.itemCount == bill) {
            orderItemsAmount.remove(event.itemId)
        } else {
            orderItemsAmount[event.itemId] = bill - event.itemCount
        }
    }

    @StateTransitionFunc
    fun expiredOrder(event: OrderExpiredEvent) {
        status = OrderStatus.DISCARD
    }

    @StateTransitionFunc
    fun orderPaid(event: OrderPaidEvent) {
        status = OrderStatus.PAID
    }
}