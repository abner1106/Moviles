package ejercicio1.biblioteca.service

import ejercicio1.biblioteca.model.Book
import ejercicio1.biblioteca.repository.LibraryRepository

class LibraryQueries(private val repository: LibraryRepository) {

    fun obtenerLibrosDisponibles(): List<Book> {
        return repository.obtenerTodosLosLibros()
            .filter { it.disponible }
    }

    fun obtenerLibrosEnPrestamo(): List<Book> {
        return repository.obtenerTodosLosLibros()
            .filter { !it.disponible }
    }

    fun obtenerLibrosPrestadosPorUsuario(userId: Int): List<Book> {
        val prestamosActivos = repository.obtenerPrestamosPorUsuario(userId)
            .filter { it.fechaDevolucion == null }

        return prestamosActivos.map { it.libro }
    }

    fun buscarLibrosPorTitulo(titulo: String): List<Book> {
        return repository.obtenerTodosLosLibros()
            .filter { it.titulo.contains(titulo, ignoreCase = true) }
    }

    fun buscarLibrosPorAutor(autor: String): List<Book> {
        return repository.obtenerTodosLosLibros()
            .filter { it.autor.contains(autor, ignoreCase = true) }
    }
}