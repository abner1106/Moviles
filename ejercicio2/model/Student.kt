package ejercicio2.cursos.model
data class Student(
    val id: Int,
    val nombre: String,
    val email: String,
    val telefono: String? = null
)