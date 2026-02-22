package ejercicio3.tienda.service

import ejercicio3.tienda.model.Order
import ejercicio3.tienda.repository.OrderRepository

class HistoryService(
    private val orderRepository: OrderRepository
) {

    fun obtenerHistorialCliente(customerId: Int): List<Order> {
        return orderRepository.obtenerHistorialCliente(customerId)
    }

    fun obtenerOrdenPorId(orderId: String): Order? {
        return orderRepository.obtenerPorId(orderId)
    }

    fun obtenerTodasLasOrdenes(): List<Order> {
        return orderRepository.obtenerTodos()
    }
}