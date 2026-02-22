package ejercicio3.tienda.repository

import ejercicio3.tienda.model.Customer

interface CustomerRepository {
    fun agregar(customer: Customer)
    fun obtenerPorId(id: Int): Customer?
    fun obtenerTodos(): List<Customer>
    fun actualizar(customer: Customer)
    fun eliminar(id: Int): Boolean
    fun buscarPorEmail(email: String): Customer?
}