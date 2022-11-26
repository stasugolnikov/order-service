package com.itmo.microservices.order.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val ORDER_CREATED = "ORDER_CREATED_EVENT"
const val POSITION_ADDED_TO_ORDER = "POSITION_ADDED_TO_ORDER_EVENT"
const val POSITION_REMOVED_FROM_ORDER = "POSITION_REMOVED_FROM_ORDER_EVENT"
const val ORDER_BOOKED = "ORDER_BOOKED_EVENT"
const val ORDER_BOOKING_CANCELED = "ORDER_BOOKING_CANCELED_EVENT"
const val ORDER_EXPIRED = "ORDER_EXPIRED_EVENT"
const val ORDER_PAID = "ORDER_PAID_EVENT"
const val ORDER_DELIVERY_STARTED = "ORDER_DELIVERY_STARTED_EVENT"
const val ORDER_DELIVERY_FAILED = "ORDER_DELIVERY_FAILED_EVENT"
const val ORDER_DELIVERY_COMPLETED = "ORDER_DELIVERY_COMPLETED_EVENT"
const val ORDER_DELIVERY_SLOT_UPDATED = "ORDER_DELIVERY_SLOT_UPDATED_EVENT"

@DomainEvent(name = ORDER_CREATED)
data class OrderCreatedEvent(
    val orderId: UUID,
    val userId: UUID,
) : Event<OrderAggregate>(
    name = ORDER_CREATED
)

@DomainEvent(name = POSITION_ADDED_TO_ORDER)
data class ItemAddedToOrderEvent(
    val orderId: UUID,
    val itemId: UUID,
    val amount: Int,
) : Event<OrderAggregate>(
    name = POSITION_ADDED_TO_ORDER
)

@DomainEvent(name = POSITION_REMOVED_FROM_ORDER)
data class ItemRemovedFromOrderEvent(
    val itemId: UUID,
    val itemCount: UUID,
    val orderId: UUID,
) : Event<OrderAggregate>(
    name = POSITION_REMOVED_FROM_ORDER
)

@DomainEvent(name = ORDER_BOOKED)
data class OrderBookedEvent(
    val orderId: UUID,
) : Event<OrderAggregate>(
    name = ORDER_BOOKED
)

@DomainEvent(name = ORDER_BOOKING_CANCELED)
data class OrderBookingCanceledEvent(
    val orderId: UUID,
) : Event<OrderAggregate>(
    name = ORDER_BOOKING_CANCELED
)

@DomainEvent(name = ORDER_EXPIRED)
data class OrderExpiredEvent(
    val orderId: UUID,
) : Event<OrderAggregate>(
    name = ORDER_EXPIRED
)

@DomainEvent(name = ORDER_PAID)
data class OrderPaidEvent(
    val orderId: UUID,
) : Event<OrderAggregate>(
    name = ORDER_PAID
)

@DomainEvent(name = ORDER_DELIVERY_STARTED)
data class OrderDeliveryStartedEvent(
    val orderId: UUID,
) : Event<OrderAggregate>(
    name = ORDER_DELIVERY_STARTED
)

@DomainEvent(name = ORDER_DELIVERY_FAILED)
data class OrderDeliveryFailedEvent(
    val orderId: UUID,
) : Event<OrderAggregate>(
    name = ORDER_DELIVERY_FAILED
)

@DomainEvent(name = ORDER_DELIVERY_COMPLETED)
data class OrderDeliveryCompletedEvent(
    val orderId: UUID,
) : Event<OrderAggregate>(
    name = ORDER_DELIVERY_COMPLETED
)

@DomainEvent(name = ORDER_DELIVERY_SLOT_UPDATED)
data class OrderDeliverySlotUpdatedEvent(
    val orderId: UUID,
    val slotInSec: Int,
) : Event<OrderAggregate>(
    name = ORDER_DELIVERY_SLOT_UPDATED
)
