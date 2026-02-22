package ejercicio3.tienda.model

data class Customer(
    val id: Int,
    val nombre: String,
    val email: String,
    val historialCompras: MutableList<Order> = mutableListOf()
)