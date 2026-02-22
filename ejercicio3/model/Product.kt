package ejercicio3.tienda.model

data class Product(
    val id: String,
    val nombre: String,
    val precio: Double,
    var stock: Int
)