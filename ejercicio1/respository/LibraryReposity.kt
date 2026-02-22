package ejercicio1.biblioteca.repository

import ejercicio1.biblioteca.model.Book
import ejercicio1.biblioteca.model.Loan
import ejercicio1.biblioteca.model.User

interface LibraryRepository {
    // Libros
    fun agregarLibro(book: Book)
    fun obtenerLibroPorIsbn(isbn: String): Book?
    fun obtenerTodosLosLibros(): List<Book>
    fun actualizarLibro(book: Book)

    // Usuarios
    fun agregarUsuario(user: User)
    fun obtenerUsuarioPorId(id: Int): User?
    fun obtenerTodosLosUsuarios(): List<User>

    // Préstamos
    fun agregarPrestamo(loan: Loan)
    fun obtenerPrestamoActivoPorLibro(isbn: String): Loan?
    fun obtenerPrestamosActivos(): List<Loan>
    fun obtenerPrestamosPorUsuario(userId: Int): List<Loan>
    fun actualizarPrestamo(loan: Loan)
}