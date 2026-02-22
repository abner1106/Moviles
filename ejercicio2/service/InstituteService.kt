package ejercicio2.cursos.service

import ejercicio2.cursos.model.Course
import ejercicio2.cursos.model.Student
import ejercicio2.cursos.model.Teacher
import ejercicio2.cursos.repository.CourseRepository
import ejercicio2.cursos.repository.EnrollmentRepository
import ejercicio2.cursos.repository.StudentRepository
import ejercicio2.cursos.repository.TeacherRepository

class InstituteService(
    private val courseRepository: CourseRepository,
    private val studentRepository: StudentRepository,
    private val teacherRepository: TeacherRepository,
    private val enrollmentRepository: EnrollmentRepository
) {

    // ===== GESTIÓN DE CURSOS =====
    fun agregarCurso(curso: Course) {
        courseRepository.agregar(curso)
    }

    fun buscarCursosPorNombre(nombre: String): List<Course> {
        return courseRepository.buscarPorNombre(nombre)
    }

    fun buscarCursosPorProfesor(profesorId: Int): List<Course> {
        return courseRepository.buscarPorProfesor(profesorId)
    }

    // ===== GESTIÓN DE ESTUDIANTES =====
    fun registrarEstudiante(estudiante: Student) {
        studentRepository.agregar(estudiante)
    }

    fun buscarEstudiantePorId(id: Int): Student? {
        return studentRepository.obtenerPorId(id)
    }

    fun buscarEstudiantesPorNombre(nombre: String): List<Student> {
        return studentRepository.buscarPorNombre(nombre)
    }

    // ===== GESTIÓN DE PROFESORES =====
    fun registrarProfesor(profesor: Teacher) {
        teacherRepository.agregar(profesor)
    }

    fun buscarProfesorPorId(id: Int): Teacher? {
        return teacherRepository.obtenerPorId(id)
    }

    fun buscarProfesoresPorEspecialidad(especialidad: String): List<Teacher> {
        return teacherRepository.buscarPorEspecialidad(especialidad)
    }

    // ===== CONSULTAS =====
    fun obtenerCursosConEstudiantes(): Map<Course, Int> {
        return courseRepository.obtenerTodos().associateWith { curso ->
            enrollmentRepository.obtenerInscripcionesPorCurso(curso.codigo).size
        }
    }

    fun obtenerEstadisticas(): InstituteStatistics {
        return InstituteStatistics(
            totalCursos = courseRepository.obtenerTodos().size,
            totalEstudiantes = studentRepository.obtenerTodos().size,
            totalProfesores = teacherRepository.obtenerTodos().size,
            totalInscripciones = enrollmentRepository.obtenerTodas().size,
            inscripcionesActivas = enrollmentRepository.obtenerInscripcionesActivas().size
        )
    }
}

data class InstituteStatistics(
    val totalCursos: Int,
    val totalEstudiantes: Int,
    val totalProfesores: Int,
    val totalInscripciones: Int,
    val inscripcionesActivas: Int
)