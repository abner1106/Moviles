package ejercicio2.cursos.repository

import ejercicio2.cursos.model.Student
import ejercicio2.cursos.exceptions.CourseExceptions

class InMemoryStudentRepository : StudentRepository {

    private val estudiantes = mutableListOf<Student>()

    override fun agregar(student: Student) {
        require(!estudiantes.any { it.id == student.id }) {
            "Ya existe un estudiante con ID ${student.id}"
        }
        estudiantes.add(student)
    }

    override fun obtenerPorId(id: Int): Student? {
        return estudiantes.find { it.id == id }
    }

    override fun obtenerTodos(): List<Student> {
        return estudiantes.toList()
    }

    override fun actualizar(student: Student) {
        val index = estudiantes.indexOfFirst { it.id == student.id }
        if (index != -1) {
            estudiantes[index] = student
        } else {
            throw CourseExceptions.EstudianteNoEncontradoException(
                "No existe estudiante con ID ${student.id}"
            )
        }
    }

    override fun eliminar(id: Int): Boolean {
        return estudiantes.removeIf { it.id == id }
    }

    override fun buscarPorNombre(nombre: String): List<Student> {
        return estudiantes.filter {
            it.nombre.contains(nombre, ignoreCase = true)
        }
    }

    override fun buscarPorEmail(email: String): Student? {
        return estudiantes.find { it.email.equals(email, ignoreCase = true) }
    }
}