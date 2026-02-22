package ejercicio3.tienda.service

import ejercicio3.tienda.model.Cart
import ejercicio3.tienda.model.Product

class CartService(
    private val cartValidator: CartValidator,
    private val validationService: ValidationService
) {

    private val carritos = mutableMapOf<Int, Cart>()

    fun obtenerCarrito(customerId: Int): Cart {
        return carritos.getOrPut(customerId) { Cart(customerId) }
    }

    fun agregarAlCarrito(customerId: Int, productId: String, cantidad: Int) {
        cartValidator.validarCantidadPositiva(cantidad)
        val product = validationService.validarStock(productId, cantidad)

        val carrito = obtenerCarrito(customerId)
        carrito.agregarItem(product, cantidad)
    }

    fun vaciarCarrito(customerId: Int) {
        carritos.remove(customerId)
    }

    fun obtenerItemsDelCarrito(customerId: Int): List<Pair<Product, Int>> {
        val carrito = obtenerCarrito(customerId)
        return carrito.items.map { Pair(it.product, it.cantidad) }
    }
}