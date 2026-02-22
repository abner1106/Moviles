package ejercicio3.tienda.model

import java.time.LocalDate

data class Order(
    val id: String,
    val customer: Customer,
    val items: List<OrderItem>,
    val fecha: LocalDate = LocalDate.now(),
    val subtotal: Double,
    val impuestos: Double,
    val total: Double
)