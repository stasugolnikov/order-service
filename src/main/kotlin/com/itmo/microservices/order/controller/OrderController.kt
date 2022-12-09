package com.itmo.microservices.order.controller

import com.itmo.microservices.order.api.OrderAggregate
import com.itmo.microservices.order.logic.OrderAggregateState
import com.itmo.microservices.order.model.AppUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(
    val orderEsService: EventSourcingService<UUID, OrderAggregate, OrderAggregateState>
) {
    @PostMapping
    fun createOrder(@AuthenticationPrincipal user: AppUser) {
        orderEsService.create { it.createOrder(user.id) }
    }

    @GetMapping("/{order_id}")
    fun getOrder(
        @PathVariable("order_id") orderId: UUID,
        @AuthenticationPrincipal user: AppUser
    ) = orderEsService.getState(orderId)

    @PutMapping("/orders/{order_id}/items/{item_id}")
    fun addItemToOrder(
        @PathVariable("order_id") orderId: UUID,
        @PathVariable("item_id") itemId: UUID,
        @RequestParam amount: Int,
        @AuthenticationPrincipal user: AppUser
    ) = orderEsService.update(orderId) { it.addItemToOrder(orderId, itemId, amount, user.id) }

    @DeleteMapping("/orders/{order_id}/items/{item_id}")
    fun deleteItemToOrder(
        @PathVariable("order_id") orderId: UUID,
        @PathVariable("item_id") itemId: UUID,
        @RequestParam amount: Int,
        @AuthenticationPrincipal user: AppUser
    ) = orderEsService.update(orderId) { it.deleteItemFromOrder(orderId, itemId, amount, user.id) }

    @PostMapping("/{order_id}/bookings")
    fun bookOrder(
        @PathVariable("order_id") orderId: UUID,
        @AuthenticationPrincipal user: AppUser
    ) = orderEsService.update(orderId) { it.bookOrder(orderId, user.id) }

    @DeleteMapping("/{order_id}/bookings")
    fun discardOrder(
        @PathVariable("order_id") orderId: UUID,
        @AuthenticationPrincipal user: AppUser
    ) = orderEsService.update(orderId) { it.discardOrder(orderId) }
}
