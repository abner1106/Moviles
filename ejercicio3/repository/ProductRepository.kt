package ejercicio3.tienda.repository

import ejercicio3.tienda.model.Product

interface ProductRepository {
    fun agregar(product: Product)
    fun obtenerPorId(id: String): Product?
    fun obtenerTodos(): List<Product>
    fun actualizar(product: Product)
    fun eliminar(id: String): Boolean
    fun buscarPorNombre(nombre: String): List<Product>
    fun descontarStock(id: String, cantidad: Int): Boolean
}