package com.itmo.microservices.order

import com.itmo.microservices.order.api.OrderAggregate
import com.itmo.microservices.order.logic.OrderAggregateState
import com.itmo.microservices.order.model.OrderStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import ru.quipy.core.EventSourcingService
import java.util.*

@SpringBootTest
class OrderAggregateStateTest {

    companion object {
        private val testId = UUID.randomUUID()
        private val userId = UUID.randomUUID()
        private val itemId = UUID.randomUUID()
        private const val itemAmount = 10
    }

    @Autowired
    private lateinit var orderESService: EventSourcingService<UUID, OrderAggregate, OrderAggregateState>

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @BeforeEach
    fun init() {
        cleanDatabase()
    }

    fun cleanDatabase() {
        mongoTemplate.remove(Query.query(Criteria.where("aggregateId").`is`(testId)), "orders")
        mongoTemplate.remove(Query.query(Criteria.where("_id").`is`(testId)), "snapshots")
    }

    @Test
    fun createOrder() {
        val event = orderESService.create {
            it.createOrder(userId.toString())
        }

        val state = orderESService.getState(event.orderId)!!

        Assertions.assertEquals(event.userId, state.getUserId())
        Assertions.assertEquals(event.orderId, state.getId())
    }

    @Test
    fun addItemToOrder() {
        val createdOrderEvent = orderESService.create {
            it.createOrder(userId.toString())
        }

        val addItemToOrder = orderESService.update(createdOrderEvent.orderId) {
            it.addItemToOrder(createdOrderEvent.orderId, itemId, itemAmount)
        }
        val state = orderESService.getState(addItemToOrder.orderId)!!

        Assertions.assertEquals(itemAmount, state.getOrderItemsAmount()[addItemToOrder.itemId])
    }

    @Test
    fun bookOrder() {
        val createdOrderEvent = orderESService.create {
            it.createOrder(userId.toString())
        }

        val bookOrderEvent = orderESService.update(createdOrderEvent.orderId) {
            it.bookOrder(createdOrderEvent.orderId)
        }
        val state = orderESService.getState(bookOrderEvent.orderId)!!

        Assertions.assertEquals(state.getStatus(), OrderStatus.BOOKED)
    }
}
