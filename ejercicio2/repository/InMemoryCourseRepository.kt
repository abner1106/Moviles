package ejercicio2.cursos.repository

import ejercicio2.cursos.model.Course
import ejercicio2.cursos.exceptions.CourseExceptions

/**
 * LSP: Puede sustituir a CourseRepository sin afectar el sistema
 */
class InMemoryCourseRepository : CourseRepository {

    private val cursos = mutableListOf<Course>()

    override fun agregar(curso: Course) {
        require(!cursos.any { it.codigo == curso.codigo }) {
            "Ya existe un curso con código ${curso.codigo}"
        }
        cursos.add(curso)
    }

    override fun obtenerPorCodigo(codigo: String): Course? {
        return cursos.find { it.codigo == codigo }
    }

    override fun obtenerTodos(): List<Course> {
        return cursos.toList()
    }

    override fun actualizar(curso: Course) {
        val index = cursos.indexOfFirst { it.codigo == curso.codigo }
        if (index != -1) {
            cursos[index] = curso
        } else {
            throw CourseExceptions.CursoNoEncontradoException(
                "No existe curso con código ${curso.codigo}"
            )
        }
    }

    override fun eliminar(codigo: String): Boolean {
        return cursos.removeIf { it.codigo == codigo }
    }

    override fun buscarPorNombre(nombre: String): List<Course> {
        return cursos.filter {
            it.nombre.contains(nombre, ignoreCase = true)
        }
    }

    override fun buscarPorProfesor(profesorId: Int): List<Course> {
        return cursos.filter { it.profesor.id == profesorId }
    }
}