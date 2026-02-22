package ejercicio1.biblioteca.model

import java.util.Date

data class Loan(
    val libro: Book,
    val usuario: User,
    val fechaPrestamo: Date = Date(),
    var fechaDevolucion: Date? = null
)