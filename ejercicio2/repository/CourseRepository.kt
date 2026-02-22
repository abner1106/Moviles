package ejercicio2.cursos.repository

import ejercicio2.cursos.model.Course

interface CourseRepository {
    fun agregar(curso: Course)
    fun obtenerPorCodigo(codigo: String): Course?
    fun obtenerTodos(): List<Course>
    fun actualizar(curso: Course)
    fun eliminar(codigo: String): Boolean
    fun buscarPorNombre(nombre: String): List<Course>
    fun buscarPorProfesor(profesorId: Int): List<Course>
}