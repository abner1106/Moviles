package ejercicio2.cursos.model
data class Course(
    val codigo: String,
    val nombre: String,
    val profesor: Teacher,
    val capacidadMaxima: Int,
    val horario: String? = null,
    val aula: String? = null
)
