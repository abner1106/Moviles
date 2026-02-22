package ejercicio3.tienda.service

import ejercicio3.tienda.exceptions.StoreExceptions
import ejercicio3.tienda.model.Cart

class CartValidator {

    fun validarCantidadPositiva(cantidad: Int) {
        if (cantidad <= 0) {
            throw StoreExceptions.CantidadInvalidaException("La cantidad debe ser positiva")
        }
    }

    fun validarCarritoNoVacio(cart: Cart) {
        if (cart.items.isEmpty()) {
            throw StoreExceptions.CarritoVacioException("El carrito está vacío")
        }
    }
}