package ejercicio2.cursos.model
data class Teacher(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val email: String? = null
)