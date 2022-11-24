package com.itmo.microservices.order.model

import java.util.*

class OrderDto {
    lateinit var id: UUID
    var timeCreated: Long = System.currentTimeMillis()
    var status: OrderStatus = OrderStatus.COLLECTING
    var itemsMap: Map<UUID, Int> = mutableMapOf()
    var deliveryDuration: Int? = null
    var paymentHistory: List<PaymentLogRecord> = mutableListOf()
}

data class PaymentLogRecord(
    val timestamp: Long,
    val status: PaymentStatus,
    val amount: Int,
    val transactionId: UUID,
)

class OrderItem {
    lateinit var id: UUID
    lateinit var title: String
    var price: Int = 0
}

class CatalogItemDto {
    lateinit var id: UUID
    lateinit var title: String
    lateinit var description: String
    var price: Int = 100
    var amount: Int = 0
}


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
