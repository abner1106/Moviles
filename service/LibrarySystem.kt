package ejercicio1.biblioteca.service

import ejercicio1.biblioteca.exceptions.LibraryExceptions
import ejercicio1.biblioteca.model.Loan
import ejercicio1.biblioteca.repository.LibraryRepository
import java.util.Date

class LibrarySystem(private val repository: LibraryRepository) {

    companion object {
        const val MAX_LIBROS_PRESTADOS = 3
    }

    fun prestarLibro(userId: Int, isbn: String): Loan {
        // Validar que el usuario existe
        val usuario = repository.obtenerUsuarioPorId(userId)
            ?: throw LibraryExceptions.UsuarioNoEncontradoException("Usuario con ID $userId no encontrado")

        // Validar que el libro existe
        val libro = repository.obtenerLibroPorIsbn(isbn)
            ?: throw LibraryExceptions.LibroNoEncontradoException("Libro con ISBN $isbn no encontrado")

        // Validar que el libro está disponible
        if (!libro.disponible) {
            throw LibraryExceptions.LibroNoDisponibleException("El libro '${libro.titulo}' no está disponible")
        }

        // Validar límite de préstamos del usuario
        val prestamosActivos = repository.obtenerPrestamosPorUsuario(userId)
            .count { it.fechaDevolucion == null }

        if (prestamosActivos >= MAX_LIBROS_PRESTADOS) {
            throw LibraryExceptions.LimitePrestamosExcedidoException(
                "El usuario '${usuario.nombre}' ya tiene $prestamosActivos préstamos activos (máximo $MAX_LIBROS_PRESTADOS)"
            )
        }

        // Crear el préstamo
        val prestamo = Loan(
            libro = libro,
            usuario = usuario,
            fechaPrestamo = Date()
        )

        // Actualizar disponibilidad del libro
        libro.disponible = false
        repository.actualizarLibro(libro)

        // Guardar el préstamo
        repository.agregarPrestamo(prestamo)

        return prestamo
    }

    fun devolverLibro(isbn: String): Loan {
        // Validar que el libro existe
        val libro = repository.obtenerLibroPorIsbn(isbn)
            ?: throw LibraryExceptions.LibroNoEncontradoException("Libro con ISBN $isbn no encontrado")

        // Validar que el libro está prestado
        val prestamoActivo = repository.obtenerPrestamoActivoPorLibro(isbn)
            ?: throw LibraryExceptions.LibroNoPrestadoException("El libro '${libro.titulo}' no está prestado actualmente")

        // Registrar devolución
        prestamoActivo.fechaDevolucion = Date()
        repository.actualizarPrestamo(prestamoActivo)

        // Liberar el libro
        libro.disponible = true
        repository.actualizarLibro(libro)

        return prestamoActivo
    }
}