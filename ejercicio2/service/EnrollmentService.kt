package ejercicio2.cursos.service

import ejercicio2.cursos.model.Course
import ejercicio2.cursos.model.Enrollment
import ejercicio2.cursos.model.Student
import ejercicio2.cursos.repository.CourseRepository
import ejercicio2.cursos.repository.EnrollmentRepository
import ejercicio2.cursos.repository.StudentRepository

/**
 * SRP: Este servicio solo se encarga de gestionar inscripciones
 */
class EnrollmentService(
    private val courseRepository: CourseRepository,
    private val studentRepository: StudentRepository,
    private val enrollmentRepository: EnrollmentRepository,
    private val validationService: ValidationService
) {

    fun inscribirEstudiante(estudianteId: Int, cursoCodigo: String): Enrollment {
        // Validar usando el servicio de validación (SRP)
        val resultado = validationService.validarInscripcion(estudianteId, cursoCodigo)

        return when (resultado) {
            is ValidationResult.Exito -> {
                val inscripcion = Enrollment(resultado.estudiante, resultado.curso)
                enrollmentRepository.agregar(inscripcion)
                inscripcion
            }
            is ValidationResult.Error -> {
                throw Exception(resultado.mensaje)
            }
        }
    }

    fun cancelarInscripcion(estudianteId: Int, cursoCodigo: String): Boolean {
        return enrollmentRepository.eliminar(estudianteId, cursoCodigo)
    }

    fun obtenerEstudiantesPorCurso(cursoCodigo: String): List<Student> {
        return enrollmentRepository
            .obtenerInscripcionesPorCurso(cursoCodigo)
            .map { it.estudiante }
    }

    fun obtenerCursosPorEstudiante(estudianteId: Int): List<Course> {
        return enrollmentRepository
            .obtenerInscripcionesPorEstudiante(estudianteId)
            .map { it.curso }
    }

    fun contarInscritosPorCurso(cursoCodigo: String): Int {
        return enrollmentRepository
            .obtenerInscripcionesPorCurso(cursoCodigo)
            .size
    }
}