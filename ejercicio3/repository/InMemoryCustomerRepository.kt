package ejercicio3.tienda.repository

import ejercicio3.tienda.model.Customer

class InMemoryCustomerRepository : CustomerRepository {

    private val clientes = mutableListOf<Customer>()

    override fun agregar(customer: Customer) {
        clientes.add(customer)
    }

    override fun obtenerPorId(id: Int): Customer? {
        return clientes.find { it.id == id }
    }

    override fun obtenerTodos(): List<Customer> {
        return clientes.toList()
    }

    override fun actualizar(customer: Customer) {
        val index = clientes.indexOfFirst { it.id == customer.id }
        if (index != -1) clientes[index] = customer
    }

    override fun eliminar(id: Int): Boolean {
        return clientes.removeIf { it.id == id }
    }

    override fun buscarPorEmail(email: String): Customer? {
        return clientes.find { it.email.equals(email, ignoreCase = true) }
    }
}