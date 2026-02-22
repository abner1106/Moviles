package ejercicio2.cursos.repository

import ejercicio2.cursos.model.Enrollment
import ejercicio2.cursos.model.EnrollmentState
import java.time.LocalDate

class InMemoryEnrollmentRepository : EnrollmentRepository {

    private val inscripciones = mutableListOf<Enrollment>()

    override fun agregar(enrollment: Enrollment) {
        require(!inscripciones.any {
            it.estudiante.id == enrollment.estudiante.id &&
                    it.curso.codigo == enrollment.curso.codigo
        }) {
            "El estudiante ${enrollment.estudiante.nombre} ya está inscrito en ${enrollment.curso.nombre}"
        }
        inscripciones.add(enrollment)
    }

    override fun obtenerPorId(estudianteId: Int, cursoCodigo: String): Enrollment? {
        return inscripciones.find {
            it.estudiante.id == estudianteId &&
                    it.curso.codigo == cursoCodigo
        }
    }

    override fun obtenerTodas(): List<Enrollment> {
        return inscripciones.toList()
    }

    override fun obtenerInscripcionesPorEstudiante(estudianteId: Int): List<Enrollment> {
        return inscripciones.filter { it.estudiante.id == estudianteId }
    }

    override fun obtenerInscripcionesPorCurso(cursoCodigo: String): List<Enrollment> {
        return inscripciones.filter { it.curso.codigo == cursoCodigo }
    }

    override fun obtenerInscripcionesPorFecha(fecha: LocalDate): List<Enrollment> {
        return inscripciones.filter { it.fechaInscripcion == fecha }
    }

    override fun obtenerInscripcionesActivas(): List<Enrollment> {
        return inscripciones.filter { it.estado == EnrollmentState.ACTIVE }
    }

    override fun actualizarEstado(estudianteId: Int, cursoCodigo: String, estado: EnrollmentState) {
        val inscripcion = obtenerPorId(estudianteId, cursoCodigo)
        if (inscripcion != null) {
            val nuevaInscripcion = inscripcion.copy(estado = estado)
            val index = inscripciones.indexOfFirst {
                it.estudiante.id == estudianteId &&
                        it.curso.codigo == cursoCodigo
            }
            if (index != -1) {
                inscripciones[index] = nuevaInscripcion
            }
        }
    }

    override fun eliminar(estudianteId: Int, cursoCodigo: String): Boolean {
        return inscripciones.removeIf {
            it.estudiante.id == estudianteId &&
                    it.curso.codigo == cursoCodigo
        }
    }
}