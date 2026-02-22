package ejercicio2.cursos.model

import java.time.LocalDate


data class Enrollment(
    val estudiante: Student,
    val curso: Course,
    val fechaInscripcion: LocalDate = LocalDate.now(),
    val estado: EnrollmentState = EnrollmentState.ACTIVE
)

enum class EnrollmentState {
    ACTIVE,
    CANCELLED,
    COMPLETED
}