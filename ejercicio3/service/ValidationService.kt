package ejercicio3.tienda.service

import ejercicio3.tienda.exceptions.StoreExceptions
import ejercicio3.tienda.model.Product
import ejercicio3.tienda.repository.ProductRepository

class ValidationService(
    private val productRepository: ProductRepository
) {

    fun validarStock(productId: String, cantidad: Int): Product {
        val product = productRepository.obtenerPorId(productId)
            ?: throw StoreExceptions.ProductoNoEncontradoException("Producto no encontrado")

        if (product.stock < cantidad) {
            throw StoreExceptions.StockInsuficienteException(
                "Stock insuficiente. Disponible: ${product.stock}, solicitado: $cantidad"
            )
        }
        return product
    }

    fun descontarStock(productId: String, cantidad: Int): Boolean {
        return productRepository.descontarStock(productId, cantidad)
    }
}