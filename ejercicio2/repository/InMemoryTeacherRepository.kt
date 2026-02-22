package ejercicio2.cursos.repository

import ejercicio2.cursos.model.Teacher

class InMemoryTeacherRepository : TeacherRepository {

    private val profesores = mutableListOf<Teacher>()

    override fun agregar(teacher: Teacher) {
        require(!profesores.any { it.id == teacher.id }) {
            "Ya existe un profesor con ID ${teacher.id}"
        }
        profesores.add(teacher)
    }

    override fun obtenerPorId(id: Int): Teacher? {
        return profesores.find { it.id == id }
    }

    override fun obtenerTodos(): List<Teacher> {
        return profesores.toList()
    }

    override fun actualizar(teacher: Teacher) {
        val index = profesores.indexOfFirst { it.id == teacher.id }
        if (index != -1) {
            profesores[index] = teacher
        }
    }

    override fun eliminar(id: Int): Boolean {
        return profesores.removeIf { it.id == id }
    }

    override fun buscarPorEspecialidad(especialidad: String): List<Teacher> {
        return profesores.filter {
            it.especialidad.contains(especialidad, ignoreCase = true)
        }
    }
}