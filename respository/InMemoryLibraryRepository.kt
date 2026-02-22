package ejercicio1.biblioteca.repository

import ejercicio1.biblioteca.model.Book
import ejercicio1.biblioteca.model.Loan
import ejercicio1.biblioteca.model.User

class InMemoryLibraryRepository : LibraryRepository {

    private val libros = mutableListOf<Book>()
    private val usuarios = mutableListOf<User>()
    private val prestamos = mutableListOf<Loan>()

    // ===== LIBROS =====
    override fun agregarLibro(book: Book) {
        libros.add(book)
    }

    override fun obtenerLibroPorIsbn(isbn: String): Book? {
        return libros.find { it.isbn == isbn }
    }

    override fun obtenerTodosLosLibros(): List<Book> {
        return libros.toList()
    }

    override fun actualizarLibro(book: Book) {
        val index = libros.indexOfFirst { it.isbn == book.isbn }
        if (index != -1) {
            libros[index] = book
        }
    }

    // ===== USUARIOS =====
    override fun agregarUsuario(user: User) {
        usuarios.add(user)
    }

    override fun obtenerUsuarioPorId(id: Int): User? {
        return usuarios.find { it.id == id }
    }

    override fun obtenerTodosLosUsuarios(): List<User> {
        return usuarios.toList()
    }

    // ===== PRÉSTAMOS =====
    override fun agregarPrestamo(loan: Loan) {
        prestamos.add(loan)
    }

    override fun obtenerPrestamoActivoPorLibro(isbn: String): Loan? {
        return prestamos.find { it.libro.isbn == isbn && it.fechaDevolucion == null }
    }

    override fun obtenerPrestamosActivos(): List<Loan> {
        return prestamos.filter { it.fechaDevolucion == null }
    }

    override fun obtenerPrestamosPorUsuario(userId: Int): List<Loan> {
        return prestamos.filter { it.usuario.id == userId }
    }

    override fun actualizarPrestamo(loan: Loan) {
        val index = prestamos.indexOfFirst {
            it.libro.isbn == loan.libro.isbn &&
                    it.usuario.id == loan.usuario.id &&
                    it.fechaPrestamo == loan.fechaPrestamo
        }
        if (index != -1) {
            prestamos[index] = loan
        }
    }
}