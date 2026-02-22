package ejercicio1.biblioteca

import ejercicio1.biblioteca.model.Book
import ejercicio1.biblioteca.model.User
import ejercicio1.biblioteca.repository.InMemoryLibraryRepository
import ejercicio1.biblioteca.service.LibrarySystem
import ejercicio1.biblioteca.service.LibraryQueries
import ejercicio1.biblioteca.exceptions.LibraryExceptions

fun main() {
    println("=== SISTEMA DE BIBLIOTECA ===")
    println("=".repeat(50))

    // Inicializar repositorio y sistemas
    val repository = InMemoryLibraryRepository()
    val librarySystem = LibrarySystem(repository)
    val libraryQueries = LibraryQueries(repository)

    try {
        // ===== CREAR LIBROS =====
        println("\n📚 AGREGANDO LIBROS")
        println("-".repeat(40))

        val libros = listOf(
            Book("El Quijote", "Miguel de Cervantes", "ISBN001"),
            Book("Cien años de soledad", "Gabriel García Márquez", "ISBN002"),
            Book("1984", "George Orwell", "ISBN003"),
            Book("Don Juan Tenorio", "José Zorrilla", "ISBN004")
        )

        libros.forEach { libro ->
            repository.agregarLibro(libro)
            println("   ✅ ${libro.titulo} - ${libro.autor}")
        }

        // ===== REGISTRAR USUARIOS =====
        println("\n👤 REGISTRANDO USUARIOS")
        println("-".repeat(40))

        val usuario1 = User(1, "Juan Pérez")
        val usuario2 = User(2, "María García")

        repository.agregarUsuario(usuario1)
        repository.agregarUsuario(usuario2)

        println("   ✅ ${usuario1.nombre} (ID: ${usuario1.id})")
        println("   ✅ ${usuario2.nombre} (ID: ${usuario2.id})")

        // ===== REALIZAR PRÉSTAMOS =====
        println("\n🔄 REALIZANDO PRÉSTAMOS")
        println("-".repeat(40))

        // Préstamos para Juan
        val prestamo1 = librarySystem.prestarLibro(usuario1.id, "ISBN001")
        println("   ✅ ${usuario1.nombre} → ${prestamo1.libro.titulo}")

        val prestamo2 = librarySystem.prestarLibro(usuario1.id, "ISBN002")
        println("   ✅ ${usuario1.nombre} → ${prestamo2.libro.titulo}")

        // Préstamo para María
        val prestamo3 = librarySystem.prestarLibro(usuario2.id, "ISBN003")
        println("   ✅ ${usuario2.nombre} → ${prestamo3.libro.titulo}")

        // ===== MOSTRAR ESTADO =====
        println("\n📊 ESTADO ACTUAL")
        println("-".repeat(40))

        println("\n📖 LIBROS DISPONIBLES:")
        libraryQueries.obtenerLibrosDisponibles().forEach { libro ->
            println("   ✅ ${libro.titulo}")
        }

        println("\n📕 LIBROS EN PRÉSTAMO:")
        libraryQueries.obtenerLibrosEnPrestamo().forEach { libro ->
            val prestamo = repository.obtenerPrestamoActivoPorLibro(libro.isbn)
            println("   📕 ${libro.titulo} → ${prestamo?.usuario?.nombre}")
        }

        // ===== DEVOLVER LIBRO =====
        println("\n🔄 DEVOLVIENDO LIBRO")
        println("-".repeat(40))

        librarySystem.devolverLibro("ISBN001")
        println("   ✅ Libro 'El Quijote' devuelto")

        // ===== ESTADO FINAL =====
        println("\n📊 ESTADO FINAL")
        println("-".repeat(40))

        println("\n📖 LIBROS DISPONIBLES:")
        libraryQueries.obtenerLibrosDisponibles().forEach { libro ->
            println("   ✅ ${libro.titulo}")
        }

        println("\n📕 LIBROS EN PRÉSTAMO:")
        libraryQueries.obtenerLibrosEnPrestamo().forEach { libro ->
            val prestamo = repository.obtenerPrestamoActivoPorLibro(libro.isbn)
            println("   📕 ${libro.titulo} → ${prestamo?.usuario?.nombre}")
        }

        // ===== VALIDACIONES =====
        println("\n⚠️ VALIDACIONES")
        println("-".repeat(40))

        try {
            librarySystem.prestarLibro(usuario1.id, "ISBN001") // Libro recién devuelto (sí disponible)
            println("   ✅ Préstamo exitoso")

            librarySystem.prestarLibro(usuario1.id, "ISBN004") // Debería ser el 3er libro (límite 3)
            println("   ✅ Tercer préstamo exitoso")

            librarySystem.prestarLibro(usuario1.id, "ISBN002") // Este debería fallar (límite excedido)
        } catch (e: LibraryExceptions.LimitePrestamosExcedidoException) {
            println("   ❌ Error: ${e.message}")
        }

    } catch (e: Exception) {
        println("❌ Error: ${e.message}")
    }

    println("\n" + "=".repeat(50))
    println("✅ PROGRAMA FINALIZADO")
}