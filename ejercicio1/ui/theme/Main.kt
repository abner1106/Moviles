package ejercicio1.biblioteca

import ejercicio1.biblioteca.exceptions.LibraryExceptions
import ejercicio1.biblioteca.model.Book
import ejercicio1.biblioteca.model.User
import ejercicio1.biblioteca.repository.InMemoryLibraryRepository
import ejercicio1.biblioteca.service.LibraryQueries
import ejercicio1.biblioteca.service.LibrarySystem

fun main() {
    println("╔══════════════════════════════════════════════════════════╗")
    println("║         SISTEMA DE BIBLIOTECA - EJERCICIO 1          ║")
    println("╚══════════════════════════════════════════════════════════╝")

    // Inicializar repositorio y sistemas
    val repository = InMemoryLibraryRepository()
    val librarySystem = LibrarySystem(repository)
    val libraryQueries = LibraryQueries(repository)

    try {
        // ===== CREAR LIBROS =====
        println("\n📚 AGREGANDO LIBROS A LA BIBLIOTECA")
        println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")

        val libros = listOf(
            Book("El Quijote", "Miguel de Cervantes", "ISBN001"),
            Book("Cien años de soledad", "Gabriel García Márquez", "ISBN002"),
            Book("1984", "George Orwell", "ISBN003"),
            Book("Don Juan Tenorio", "José Zorrilla", "ISBN004"),
            Book("La Odisea", "Homero", "ISBN005"),
            Book("Hamlet", "William Shakespeare", "ISBN006")
        )

        libros.forEach { libro ->
            repository.agregarLibro(libro)
            println("   ✅ ${libro.titulo} - ${libro.autor} [${libro.isbn}]")
        }

        // ===== REGISTRAR USUARIOS =====
        println("\n👤 REGISTRANDO USUARIOS")
        println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")

        val usuarios = listOf(
            User(1, "Juan Pérez"),
            User(2, "María García"),
            User(3, "Carlos López"),
            User(4, "Ana Martínez")
        )

        usuarios.forEach { usuario ->
            repository.agregarUsuario(usuario)
            println("   ✅ ${usuario.nombre} (ID: ${usuario.id})")
        }

        // ===== MOSTRAR LIBROS DISPONIBLES =====
        mostrarSeccion("LIBROS DISPONIBLES INICIALMENTE")
        libraryQueries.obtenerLibrosDisponibles().forEachIndexed { i, libro ->
            println("   ${i + 1}. 📖 ${libro.titulo} - ${libro.autor}")
        }

        // ===== REALIZAR PRÉSTAMOS =====
        mostrarSeccion("REALIZANDO PRÉSTAMOS")

        // Préstamo 1: Juan toma El Quijote
        val prestamo1 = librarySystem.prestarLibro(1, "ISBN001")
        println("   ✅ ${prestamo1.usuario.nombre} → ${prestamo1.libro.titulo}")

        // Préstamo 2: Juan toma Cien años de soledad
        val prestamo2 = librarySystem.prestarLibro(1, "ISBN002")
        println("   ✅ ${prestamo2.usuario.nombre} → ${prestamo2.libro.titulo}")

        // Préstamo 3: María toma 1984
        val prestamo3 = librarySystem.prestarLibro(2, "ISBN003")
        println("   ✅ ${prestamo3.usuario.nombre} → ${prestamo3.libro.titulo}")

        // Préstamo 4: Carlos toma Don Juan Tenorio
        val prestamo4 = librarySystem.prestarLibro(3, "ISBN004")
        println("   ✅ ${prestamo4.usuario.nombre} → ${prestamo4.libro.titulo}")

        // Préstamo 5: Ana toma La Odisea
        val prestamo5 = librarySystem.prestarLibro(4, "ISBN005")
        println("   ✅ ${prestamo5.usuario.nombre} → ${prestamo5.libro.titulo}")

        // ===== MOSTRAR ESTADO ACTUAL =====
        mostrarSeccion("ESTADO ACTUAL DE LA BIBLIOTECA")

        println("\n📖 LIBROS DISPONIBLES:")
        val disponibles = libraryQueries.obtenerLibrosDisponibles()
        if (disponibles.isNotEmpty()) {
            disponibles.forEach { libro ->
                println("   ✅ ${libro.titulo} - ${libro.autor}")
            }
        } else {
            println("   ❌ No hay libros disponibles")
        }

        println("\n📕 LIBROS EN PRÉSTAMO:")
        libraryQueries.obtenerLibrosEnPrestamo().forEach { libro ->
            val prestamo = repository.obtenerPrestamoActivoPorLibro(libro.isbn)
            println("   📕 ${libro.titulo} → ${prestamo?.usuario?.nombre}")
        }

        // ===== MOSTRAR PRÉSTAMOS POR USUARIO =====
        mostrarSeccion("PRÉSTAMOS POR USUARIO")

        usuarios.forEach { usuario ->
            val librosUsuario = libraryQueries.obtenerLibrosPrestadosPorUsuario(usuario.id)
            println("\n👤 ${usuario.nombre} (ID: ${usuario.id}):")
            if (librosUsuario.isNotEmpty()) {
                librosUsuario.forEach { libro ->
                    println("   📕 ${libro.titulo}")
                }
            } else {
                println("   No tiene libros prestados")
            }
        }

        // ===== DEVOLVER UN LIBRO =====
        mostrarSeccion("DEVOLVIENDO UN LIBRO")

        val libroDevuelto = librarySystem.devolverLibro("ISBN001")
        println("   ✅ '${libroDevuelto.libro.titulo}' devuelto por ${libroDevuelto.usuario.nombre}")

        // ===== MOSTRAR ESTADO DESPUÉS DE DEVOLUCIÓN =====
        mostrarSeccion("ESTADO DESPUÉS DE DEVOLUCIÓN")

        println("\n📖 LIBROS DISPONIBLES AHORA:")
        libraryQueries.obtenerLibrosDisponibles().forEach { libro ->
            println("   ✅ ${libro.titulo}")
        }

        // ===== DEMOSTRACIÓN DE VALIDACIONES =====
        mostrarSeccion("DEMOSTRACIÓN DE VALIDACIONES (PRINCIPIOS SOLID)")

        // Validación 1: Libro no disponible
        println("\n🔍 Caso 1: Prestar libro ya prestado (ISBN002)")
        try {
            librarySystem.prestarLibro(2, "ISBN002")
        } catch (e: LibraryExceptions.LibroNoDisponibleException) {
            println("   ❌ Error capturado: ${e.message}")
            println("   ✅ SRP: LibrarySystem delega la validación")
        }

        // Validación 2: Límite de préstamos
        println("\n🔍 Caso 2: Exceder límite de 3 préstamos (Juan)")
        try {
            // Juan tiene 1 libro (devolvió ISBN001, le queda ISBN002)
            librarySystem.prestarLibro(1, "ISBN006") // Segundo libro
            println("   ✅ Segundo préstamo exitoso")

            librarySystem.prestarLibro(1, "ISBN001") // Tercer libro (está disponible)
            println("   ✅ Tercer préstamo exitoso")

            // Intentar cuarto préstamo
            librarySystem.prestarLibro(1, "ISBN003")
        } catch (e: LibraryExceptions.LimitePrestamosExcedidoException) {
            println("   ❌ Error capturado: ${e.message}")
            println("   ✅ OCP: La política de límite está encapsulada")
        } catch (e: LibraryExceptions.LibroNoDisponibleException) {
            println("   ❌ Error: ${e.message}")
        }

        // Validación 3: Usuario inexistente
        println("\n🔍 Caso 3: Usuario inexistente (ID: 999)")
        try {
            librarySystem.prestarLibro(999, "ISBN004")
        } catch (e: LibraryExceptions.UsuarioNoEncontradoException) {
            println("   ❌ Error capturado: ${e.message}")
            println("   ✅ DIP: Repository es una abstracción")
        }

        // ===== RESUMEN FINAL =====
        mostrarSeccion("RESUMEN FINAL - PRINCIPIOS SOLID APLICADOS")

        println("\n📊 ESTADÍSTICAS:")
        println("   📚 Total libros: ${repository.obtenerTodosLosLibros().size}")
        println("   📖 Disponibles: ${libraryQueries.obtenerLibrosDisponibles().size}")
        println("   📕 En préstamo: ${libraryQueries.obtenerLibrosEnPrestamo().size}")
        println("   👥 Total usuarios: ${repository.obtenerTodosLosUsuarios().size}")
        println("   🔄 Préstamos activos: ${repository.obtenerPrestamosActivos().size}")

        println("\n" + "╔══════════════════════════════════════════════════════════╗")
        println("   ✅ S - Single Responsibility: Cada clase tiene una función")
        println("   ✅ O - Open/Closed: Políticas extensibles sin modificar")
        println("   ✅ L - Liskov: Repositorios pueden intercambiarse")
        println("   ✅ I - Interface Segregation: Interfaces específicas")
        println("   ✅ D - Dependency Inversion: Dependemos de abstracciones")
        println("╚══════════════════════════════════════════════════════════╝")

    } catch (e: Exception) {
        println("❌ Error inesperado: ${e.message}")
        e.printStackTrace()
    }

    println("\n✅ PROGRAMA FINALIZADO")
}

fun mostrarSeccion(titulo: String) {
    println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
    println("   $titulo")
    println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
}