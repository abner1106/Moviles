package ejercicio3.tienda.repository

import ejercicio3.tienda.model.Order
import java.util.UUID

class InMemoryOrderRepository : OrderRepository {

    private val ordenes = mutableListOf<Order>()

    override fun agregar(order: Order) {
        ordenes.add(order)
    }

    override fun obtenerPorId(id: String): Order? {
        return ordenes.find { it.id == id }
    }

    override fun obtenerTodos(): List<Order> {
        return ordenes.toList()
    }

    override fun obtenerPorCliente(customerId: Int): List<Order> {
        return ordenes.filter { it.customer.id == customerId }
    }

    override fun obtenerHistorialCliente(customerId: Int): List<Order> {
        return ordenes.filter { it.customer.id == customerId }
    }
}