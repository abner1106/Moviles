package ejercicio3.tienda.repository

import ejercicio3.tienda.model.Product

class InMemoryProductRepository : ProductRepository {

    private val productos = mutableListOf<Product>()

    override fun agregar(product: Product) {
        productos.add(product)
    }

    override fun obtenerPorId(id: String): Product? {
        return productos.find { it.id == id }
    }

    override fun obtenerTodos(): List<Product> {
        return productos.toList()
    }

    override fun actualizar(product: Product) {
        val index = productos.indexOfFirst { it.id == product.id }
        if (index != -1) productos[index] = product
    }

    override fun eliminar(id: String): Boolean {
        return productos.removeIf { it.id == id }
    }

    override fun buscarPorNombre(nombre: String): List<Product> {
        return productos.filter { it.nombre.contains(nombre, ignoreCase = true) }
    }

    override fun descontarStock(id: String, cantidad: Int): Boolean {
        val product = obtenerPorId(id) ?: return false
        if (product.stock < cantidad) return false
        product.stock -= cantidad
        actualizar(product)
        return true
    }
}