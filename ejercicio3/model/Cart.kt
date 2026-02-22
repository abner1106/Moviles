package ejercicio3.tienda.model

data class Cart(
    val customerId: Int,
    val items: MutableList<OrderItem> = mutableListOf()
) {
    fun agregarItem(product: Product, cantidad: Int) {
        items.add(OrderItem(product, cantidad, product.precio))
    }

    fun vaciar() {
        items.clear()
    }

    val subtotal: Double
        get() = items.sumOf { it.subtotal }
}