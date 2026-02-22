package ejercicio2.cursos.service

import ejercicio2.cursos.exceptions.CourseExceptions
import ejercicio2.cursos.model.Course
import ejercicio2.cursos.model.Student
import ejercicio2.cursos.repository.CourseRepository
import ejercicio2.cursos.repository.EnrollmentRepository
import ejercicio2.cursos.repository.StudentRepository
import ejercicio2.cursos.utils.Constants

class ValidationService(
    private val courseRepository: CourseRepository,
    private val studentRepository: StudentRepository,
    private val enrollmentRepository: EnrollmentRepository
) {

    fun validarInscripcion(estudianteId: Int, cursoCodigo: String): ValidationResult {
        val curso = validarCursoExistente(cursoCodigo)
        val estudiante = validarEstudianteExistente(estudianteId)

        validarInscripcionDuplicada(estudianteId, cursoCodigo)
        validarCupoDisponible(cursoCodigo, curso.capacidadMaxima)

        return ValidationResult.Exito(curso, estudiante)
    }

    private fun validarCursoExistente(codigo: String): Course {
        return courseRepository.obtenerPorCodigo(codigo)
            ?: throw CourseExceptions.CursoNoEncontradoException(
                "No existe curso con código $codigo"
            )
    }

    private fun validarEstudianteExistente(id: Int): Student {
        return studentRepository.obtenerPorId(id)
            ?: throw CourseExceptions.EstudianteNoEncontradoException(
                "No existe estudiante con ID $id"
            )
    }

    private fun validarInscripcionDuplicada(estudianteId: Int, cursoCodigo: String) {
        val inscripcionExistente = enrollmentRepository.obtenerPorId(estudianteId, cursoCodigo)
        if (inscripcionExistente != null) {
            throw CourseExceptions.InscripcionDuplicadaException(
                "El estudiante ya está inscrito en este curso"
            )
        }
    }

    private fun validarCupoDisponible(cursoCodigo: String, capacidadMaxima: Int) {
        val inscritosActuales = enrollmentRepository
            .obtenerInscripcionesPorCurso(cursoCodigo)
            .size

        if (inscritosActuales >= capacidadMaxima) {
            throw CourseExceptions.CupoLlenoException(
                "El curso ha alcanzado su capacidad máxima ($capacidadMaxima estudiantes)"
            )
        }
    }

    fun validarEstudiantePuedeInscribirse(estudianteId: Int): Boolean {
        val inscripcionesActivas = enrollmentRepository
            .obtenerInscripcionesPorEstudiante(estudianteId)
            .size

        return inscripcionesActivas < Constants.MAX_CURSOS_POR_ESTUDIANTE
    }
}

sealed class ValidationResult {
    data class Exito(val curso: Course, val estudiante: Student) : ValidationResult()
    data class Error(val mensaje: String) : ValidationResult()
}