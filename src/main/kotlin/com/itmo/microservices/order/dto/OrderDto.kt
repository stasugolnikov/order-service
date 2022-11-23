package com.itmo.microservices.order.dto

import java.util.*

data class OrderDto(
    val id: UUID,
    val timeCreated: Long,
    val status: OrderStatus,
    val itemsMap: Map<UUID, Int>,
    val deliveryDuration: Int?,
    val paymentHistory: List<PaymentLogRecordDto>
)

data class PaymentLogRecordDto(
    val timestamp: Long,
    val status: PaymentStatus,
    val amount: Int,
    val transactionId: UUID,
)

enum class OrderStatus {
    COLLECTING,
    DISCARD,
    BOOKED,
    PAID,
    SHIPPING,
    REFUND,
    COMPLETED
}

enum class PaymentStatus {
    SUCCESS,
    FAILED
}
