package ejercicio2.cursos.utils

object Constants {
    const val MAX_ESTUDIANTES = 30
    const val MAX_CURSOS_POR_ESTUDIANTE = 5
    const val MIN_ESTUDIANTES_POR_CURSO = 5

    object Messages {
        const val CURSO_NO_ENCONTRADO = "Curso no encontrado"
        const val ESTUDIANTE_NO_ENCONTRADO = "Estudiante no encontrado"
        const val INSCRIPCION_DUPLICADA = "El estudiante ya está inscrito en este curso"
        const val CUPO_LLENO = "El curso ha alcanzado su capacidad máxima"
    }
}