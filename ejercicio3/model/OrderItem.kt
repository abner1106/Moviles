package ejercicio3.tienda.model

data class OrderItem(
    val product: Product,
    val cantidad: Int,
    val precioUnitario: Double
) {
    val subtotal: Double
        get() = precioUnitario * cantidad
}