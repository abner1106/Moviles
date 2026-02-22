package ejercicio3.tienda.repository

import ejercicio3.tienda.model.Order

interface OrderRepository {
    fun agregar(order: Order)
    fun obtenerPorId(id: String): Order?
    fun obtenerTodos(): List<Order>
    fun obtenerPorCliente(customerId: Int): List<Order>
    fun obtenerHistorialCliente(customerId: Int): List<Order>
}