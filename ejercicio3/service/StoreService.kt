package ejercicio3.tienda.service

import ejercicio3.tienda.exceptions.StoreExceptions
import ejercicio3.tienda.model.Order
import ejercicio3.tienda.repository.CustomerRepository
import ejercicio3.tienda.repository.OrderRepository
import java.time.LocalDate
import java.util.UUID

class StoreService(
    private val customerRepository: CustomerRepository,
    private val orderRepository: OrderRepository,
    private val cartService: CartService,
    private val cartValidator: CartValidator,
    private val validationService: ValidationService,
    private val taxService: TaxService
) {

    fun procesarCompra(customerId: Int): Order {
        // 1. Validar cliente
        val customer = customerRepository.obtenerPorId(customerId)
            ?: throw StoreExceptions.ClienteNoEncontradoException("Cliente con ID $customerId no encontrado")

        // 2. Obtener carrito y validar
        val carrito = cartService.obtenerCarrito(customerId)
        cartValidator.validarCarritoNoVacio(carrito)

        // 3. Descontar stock
        carrito.items.forEach { item ->
            validationService.descontarStock(item.product.id, item.cantidad)
        }

        // 4. Calcular totales
        val subtotal = carrito.subtotal
        val impuestos = taxService.calcularImpuestos(subtotal)
        val total = subtotal + impuestos

        // 5. Crear orden
        val order = Order(
            id = UUID.randomUUID().toString(),
            customer = customer,
            items = carrito.items.toList(),
            fecha = LocalDate.now(),
            subtotal = subtotal,
            impuestos = impuestos,
            total = total
        )

        // 6. Guardar orden
        orderRepository.agregar(order)
        customer.historialCompras.add(order)

        // 7. Vaciar carrito
        cartService.vaciarCarrito(customerId)

        return order
    }
}