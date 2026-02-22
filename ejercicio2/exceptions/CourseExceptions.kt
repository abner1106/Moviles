package ejercicio2.cursos.exceptions

/**
 * Jerarquía de excepciones específicas del dominio
 */
sealed class CourseException(message: String) : Exception(message)

object CourseExceptions {

    class CursoNoEncontradoException(message: String) : CourseException(message)

    class EstudianteNoEncontradoException(message: String) : CourseException(message)

    class ProfesorNoEncontradoException(message: String) : CourseException(message)

    class InscripcionDuplicadaException(message: String) : CourseException(message)

    class CupoLlenoException(message: String) : CourseException(message)

    class EstudianteSinCupoException(message: String) : CourseException(message)

    class CursoSinProfesorException(message: String) : CourseException(message)

    class FechaInvalidaException(message: String) : CourseException(message)
}