package ejercicio2.cursos.repository

import ejercicio2.cursos.model.Student
interface StudentRepository {
    fun agregar(student: Student)
    fun obtenerPorId(id: Int): Student?
    fun obtenerTodos(): List<Student>
    fun actualizar(student: Student)
    fun eliminar(id: Int): Boolean
    fun buscarPorNombre(nombre: String): List<Student>
    fun buscarPorEmail(email: String): Student?
}