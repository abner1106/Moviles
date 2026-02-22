package ejercicio2.cursos.repository

import ejercicio2.cursos.model.Teacher

interface TeacherRepository {
    fun agregar(teacher: Teacher)
    fun obtenerPorId(id: Int): Teacher?
    fun obtenerTodos(): List<Teacher>
    fun actualizar(teacher: Teacher)
    fun eliminar(id: Int): Boolean
    fun buscarPorEspecialidad(especialidad: String): List<Teacher>
}