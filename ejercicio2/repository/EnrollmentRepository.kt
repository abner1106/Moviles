package ejercicio2.cursos.repository

import ejercicio2.cursos.model.Enrollment
import ejercicio2.cursos.model.EnrollmentState
import java.time.LocalDate

/**
 * ISP: Interfaz específica para operaciones con inscripciones
 */
interface EnrollmentRepository {
    fun agregar(enrollment: Enrollment)
    fun obtenerPorId(estudianteId: Int, cursoCodigo: String): Enrollment?
    fun obtenerTodas(): List<Enrollment>
    fun obtenerInscripcionesPorEstudiante(estudianteId: Int): List<Enrollment>
    fun obtenerInscripcionesPorCurso(cursoCodigo: String): List<Enrollment>
    fun obtenerInscripcionesPorFecha(fecha: LocalDate): List<Enrollment>
    fun obtenerInscripcionesActivas(): List<Enrollment>
    fun actualizarEstado(estudianteId: Int, cursoCodigo: String, estado: EnrollmentState)
    fun eliminar(estudianteId: Int, cursoCodigo: String): Boolean
}

