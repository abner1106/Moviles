package ejercicio2.cursos

import ejercicio2.cursos.exceptions.CourseExceptions
import ejercicio2.cursos.model.Course
import ejercicio2.cursos.model.Student
import ejercicio2.cursos.model.Teacher
import ejercicio2.cursos.repository.InMemoryCourseRepository
import ejercicio2.cursos.repository.InMemoryEnrollmentRepository
import ejercicio2.cursos.repository.InMemoryStudentRepository
import ejercicio2.cursos.repository.InMemoryTeacherRepository
import ejercicio2.cursos.service.EnrollmentService
import ejercicio2.cursos.service.InstituteService
import ejercicio2.cursos.service.ValidationService
import ejercicio2.cursos.utils.Constants

fun main() {
    println("╔══════════════════════════════════════════════════════════╗")
    println("║     SISTEMA DE GESTIÓN DE CURSOS - EJERCICIO 2              ║")
    println("║                       ║")
    println("╚══════════════════════════════════════════════════════════╝")

    // Inicializar repositorios (DIP: Dependemos de abstracciones)
    val courseRepository = InMemoryCourseRepository()
    val studentRepository = InMemoryStudentRepository()
    val teacherRepository = InMemoryTeacherRepository()
    val enrollmentRepository = InMemoryEnrollmentRepository()

    // Inicializar servicios (SRP: Cada servicio tiene una responsabilidad)
    val validationService = ValidationService(
        courseRepository,
        studentRepository,
        enrollmentRepository
    )
    val enrollmentService = EnrollmentService(
        courseRepository,
        studentRepository,
        enrollmentRepository,
        validationService
    )
    val instituteService = InstituteService(
        courseRepository,
        studentRepository,
        teacherRepository,
        enrollmentRepository
    )

    try {
        // ===== CREAR PROFESORES =====
        mostrarSeccion("REGISTRANDO PROFESORES")

        val profesores = listOf(
            Teacher(101, "Dr. Ambrosio Cardoso", "Desarrollo Móvil"),
            Teacher(102, "Mtra. Laura Sánchez", "Bases de Datos"),
            Teacher(103, "Ing. Roberto Gómez", "Redes"),
            Teacher(104, "Dr. Miguel Ángel", "Algoritmos")
        )

        profesores.forEach { profesor ->
            instituteService.registrarProfesor(profesor)
            println("   ✅ ${profesor.nombre} - ${profesor.especialidad}")
        }

        // ===== CREAR CURSOS =====
        mostrarSeccion("CREANDO CURSOS")

        val cursos = listOf(
            Course("C001", "Desarrollo Móvil Avanzado", profesores[0], Constants.MAX_ESTUDIANTES),
            Course("C002", "Bases de Datos NoSQL", profesores[1], 25),
            Course("C003", "Seguridad en Redes", profesores[2], 20),
            Course("C004", "Estructuras de Datos", profesores[3], 30),
            Course("C005", "Inteligencia Artificial", profesores[0], 15)
        )

        cursos.forEach { curso ->
            instituteService.agregarCurso(curso)
            println("   📚 ${curso.nombre} (${curso.codigo}) - Profesor: ${curso.profesor.nombre}")
            println("      Cupo máximo: ${curso.capacidadMaxima} estudiantes")
        }

        // ===== REGISTRAR ESTUDIANTES =====
        mostrarSeccion("REGISTRANDO ESTUDIANTES")

        val estudiantes = listOf(
            Student(2022001, "Juan Pérez", "juan@email.com"),
            Student(2022002, "María García", "maria@email.com"),
            Student(2022003, "Carlos López", "carlos@email.com"),
            Student(2022004, "Ana Martínez", "ana@email.com"),
            Student(2022005, "Luis Rodríguez", "luis@email.com"),
            Student(2022006, "Sofía Torres", "sofia@email.com"),
            Student(2022007, "Diego Ramírez", "diego@email.com"),
            Student(2022008, "Valentina Castro", "valentina@email.com")
        )

        estudiantes.forEach { estudiante ->
            instituteService.registrarEstudiante(estudiante)
            println("   👨‍🎓 ${estudiante.nombre} (ID: ${estudiante.id}) - ${estudiante.email}")
        }

        // ===== REALIZAR INSCRIPCIONES =====
        mostrarSeccion("REALIZANDO INSCRIPCIONES")

        // Inscripción 1: Juan a Desarrollo Móvil
        try {
            val inscripcion1 = enrollmentService.inscribirEstudiante(2022001, "C001")
            println("   ✅ ${inscripcion1.estudiante.nombre} → ${inscripcion1.curso.nombre}")
        } catch (e: Exception) {
            println("   ❌ Error: ${e.message}")
        }

        // Inscripción 2: María a Desarrollo Móvil
        try {
            val inscripcion2 = enrollmentService.inscribirEstudiante(2022002, "C001")
            println("   ✅ ${inscripcion2.estudiante.nombre} → ${inscripcion2.curso.nombre}")
        } catch (e: Exception) {
            println("   ❌ Error: ${e.message}")
        }

        // Inscripción 3: Carlos a Bases de Datos
        try {
            val inscripcion3 = enrollmentService.inscribirEstudiante(2022003, "C002")
            println("   ✅ ${inscripcion3.estudiante.nombre} → ${inscripcion3.curso.nombre}")
        } catch (e: Exception) {
            println("   ❌ Error: ${e.message}")
        }

        // Inscripción 4: Ana a Seguridad en Redes
        try {
            val inscripcion4 = enrollmentService.inscribirEstudiante(2022004, "C003")
            println("   ✅ ${inscripcion4.estudiante.nombre} → ${inscripcion4.curso.nombre}")
        } catch (e: Exception) {
            println("   ❌ Error: ${e.message}")
        }

        // Inscripción 5: Luis a Desarrollo Móvil (mismo curso)
        try {
            val inscripcion5 = enrollmentService.inscribirEstudiante(2022005, "C001")
            println("   ✅ ${inscripcion5.estudiante.nombre} → ${inscripcion5.curso.nombre}")
        } catch (e: Exception) {
            println("   ❌ Error: ${e.message}")
        }

        // Inscripción 6: Sofía a Inteligencia Artificial
        try {
            val inscripcion6 = enrollmentService.inscribirEstudiante(2022006, "C005")
            println("   ✅ ${inscripcion6.estudiante.nombre} → ${inscripcion6.curso.nombre}")
        } catch (e: Exception) {
            println("   ❌ Error: ${e.message}")
        }

        // ===== MOSTRAR ESTADO ACTUAL =====
        mostrarSeccion("ESTADO ACTUAL DE CURSOS")

        cursos.forEach { curso ->
            val inscritos = enrollmentRepository.obtenerInscripcionesPorCurso(curso.codigo).size
            println("\n📚 ${curso.nombre} (${curso.codigo})")
            println("   Profesor: ${curso.profesor.nombre}")
            println("   Cupo: ${inscritos}/${curso.capacidadMaxima} estudiantes")

            if (inscritos > 0) {
                println("   Estudiantes inscritos:")
                enrollmentRepository.obtenerInscripcionesPorCurso(curso.codigo).forEach { inscripcion ->
                    println("      • ${inscripcion.estudiante.nombre}")
                }
            }
        }

        // ===== MOSTRAR CURSOS POR ESTUDIANTE =====
        mostrarSeccion("CURSOS POR ESTUDIANTE")

        listOf(2022001, 2022002, 2022003).forEach { studentId ->
            val estudiante = studentRepository.obtenerPorId(studentId)
            if (estudiante != null) {
                val cursosEstudiante = enrollmentService.obtenerCursosPorEstudiante(studentId)
                println("\n👨‍🎓 ${estudiante.nombre}:")
                if (cursosEstudiante.isNotEmpty()) {
                    cursosEstudiante.forEach { curso ->
                        println("   📚 ${curso.nombre}")
                    }
                } else {
                    println("   No está inscrito en ningún curso")
                }
            }
        }

        // ===== DEMOSTRACIÓN DE VALIDACIONES (PRINCIPIOS SOLID) =====
        mostrarSeccion("DEMOSTRACIÓN DE VALIDACIONES - PRINCIPIOS SOLID")

        // CASO 1: Inscripción duplicada (SRP - ValidationService maneja esta validación)
        println("\n🔍 CASO 1: Inscripción duplicada (Mismo estudiante al mismo curso)")
        try {
            enrollmentService.inscribirEstudiante(2022001, "C001") // Juan ya está en Desarrollo Móvil
        } catch (e: CourseExceptions.InscripcionDuplicadaException) {
            println("   ❌ Error: ${e.message}")
            println("   ✅ SRP: ValidationService detectó la duplicación")
        }

        // CASO 2: Cupo excedido (OCP - Podemos cambiar la política de cupo sin modificar el servicio)
        println("\n🔍 CASO 2: Exceder cupo máximo del curso")
        try {
            // Llenar el curso hasta el límite
            repeat(12) { i ->
                val studentId = 2022010 + i
                studentRepository.agregar(Student(studentId, "Estudiante $i", "est$i@email.com"))
                enrollmentService.inscribirEstudiante(studentId, "C005") // Curso con cupo 15
            }
            println("   ✅ Curso llenado correctamente")
        } catch (e: CourseExceptions.CupoLlenoException) {
            println("   ❌ Error: ${e.message}")
            println("   ✅ OCP: La política de cupo está encapsulada y puede cambiarse")
        }

        // CASO 3: Curso no encontrado (ISP - Repository específico para cursos)
        println("\n🔍 CASO 3: Curso inexistente")
        try {
            enrollmentService.inscribirEstudiante(2022001, "C999")
        } catch (e: CourseExceptions.CursoNoEncontradoException) {
            println("   ❌ Error: ${e.message}")
            println("   ✅ ISP: CourseRepository tiene interfaz específica")
        }

        // CASO 4: Estudiante no encontrado (DIP - Dependemos de abstracciones)
        println("\n🔍 CASO 4: Estudiante inexistente")
        try {
            enrollmentService.inscribirEstudiante(999999, "C001")
        } catch (e: CourseExceptions.EstudianteNoEncontradoException) {
            println("   ❌ Error: ${e.message}")
            println("   ✅ DIP: StudentRepository es una abstracción")
        }

        // ===== RESUMEN FINAL =====
        mostrarSeccion("RESUMEN FINAL - ESTADÍSTICAS")

        println("\n📊 ESTADÍSTICAS DEL INSTITUTO:")
        println("   📚 Total cursos: ${courseRepository.obtenerTodos().size}")
        println("   👨‍🏫 Total profesores: ${teacherRepository.obtenerTodos().size}")
        println("   👨‍🎓 Total estudiantes: ${studentRepository.obtenerTodos().size}")
        println("   📝 Total inscripciones: ${enrollmentRepository.obtenerTodas().size}")

        println("\n📊 INSCRIPCIONES POR CURSO:")
        courseRepository.obtenerTodos().forEach { curso ->
            val count = enrollmentRepository.obtenerInscripcionesPorCurso(curso.codigo).size
            val porcentaje = (count * 100) / curso.capacidadMaxima
            println("   ${curso.nombre}: $count/${curso.capacidadMaxima} ($porcentaje%)")
        }

        // ===== PRINCIPIOS SOLID APLICADOS =====
        println("\n" + "╔══════════════════════════════════════════════════════════╗")
        println("║     PRINCIPIOS SOLID APLICADOS EN EL SISTEMA            ║")
        println("╠══════════════════════════════════════════════════════════╣")
        println("║  ✅ SRP: Cada clase tiene una responsabilidad única      ║")
        println("║     - Course: solo datos del curso                       ║")
        println("║     - ValidationService: solo validaciones               ║")
        println("║     - EnrollmentService: solo inscripciones              ║")
        println("╠══════════════════════════════════════════════════════════╣")
        println("║  ✅ OCP: Abierto a extensión, cerrado a modificación    ║")
        println("║     - Podemos agregar nuevas validaciones sin cambiar    ║")
        println("║       EnrollmentService                                   ║")
        println("╠══════════════════════════════════════════════════════════╣")
        println("║  ✅ LSP: Las implementaciones pueden sustituir           ║")
        println("║     interfaces (ej: repositorios en memoria vs BD)       ║")
        println("╠══════════════════════════════════════════════════════════╣")
        println("║  ✅ ISP: Interfaces específicas por entidad              ║")
        println("║     - CourseRepository, StudentRepository, etc.          ║")
        println("╠══════════════════════════════════════════════════════════╣")
        println("║  ✅ DIP: Dependemos de abstracciones, no de               ║")
        println("║     implementaciones concretas                           ║")
        println("╚══════════════════════════════════════════════════════════╝")

    } catch (e: Exception) {
        println("❌ Error inesperado: ${e.message}")
        e.printStackTrace()
    }

    println("\n✅ SISTEMA DE GESTIÓN DE CURSOS FINALIZADO")
}

fun mostrarSeccion(titulo: String) {
    println("\n" + "═".repeat(40))
    println("   $titulo")
    println("═".repeat(40))
}