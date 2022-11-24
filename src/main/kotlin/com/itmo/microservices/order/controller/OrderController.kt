package com.itmo.microservices.order.controller

import com.itmo.microservices.order.api.OrderAggregate
import com.itmo.microservices.order.logic.OrderAggregateState
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(
    val orderEsService: EventSourcingService<UUID, OrderAggregate, OrderAggregateState>
) {
    @PostMapping
    fun createOrder(@RequestHeader(AUTHORIZATION) auth: String) = orderEsService.create { it.createOrder(auth) }

    @GetMapping("/{order_id}")
    fun getOrder(
        @PathVariable("order_id") orderId: UUID,
        @RequestHeader(AUTHORIZATION) auth: String,
    ) = orderEsService.getState(orderId)

    @PutMapping("/orders/{order_id}/items/{item_id}")
    fun addItemToOrder(
        @PathVariable("order_id") orderId: UUID,
        @PathVariable("item_id") itemId: UUID,
        @RequestParam amount: Int,
        @RequestHeader(AUTHORIZATION) auth: String,
    ) = orderEsService.update(orderId) { it.addItemToOrder(orderId, itemId, amount) }

    @PostMapping("/{order_id}/bookings")
    fun bookOrder(
        @PathVariable("order_id") orderId: UUID,
        @RequestHeader(AUTHORIZATION) auth: String,
    ) = orderEsService.update(orderId) { it.bookOrder(orderId) }

    @PostMapping("/{order_id}/delivery")
    fun updateDeliverySlot(
        @PathVariable("order_id") orderId: UUID,
        @RequestParam("slot_in_sec") slotInSec: Int,
        @RequestHeader(AUTHORIZATION) auth: String,
    ) = orderEsService.update(orderId) { it.updateDeliverySlot(orderId, slotInSec) }
}