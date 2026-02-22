package ejercicio1.biblioteca.model

data class Book(
    val titulo: String,
    val autor: String,
    val isbn: String,
    var disponible: Boolean = true
)