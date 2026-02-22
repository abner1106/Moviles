package ejercicio1.biblioteca.exceptions

class LibraryExceptions {

    class UsuarioNoEncontradoException(mensaje: String) : Exception(mensaje)

    class LibroNoEncontradoException(mensaje: String) : Exception(mensaje)

    class LibroNoDisponibleException(mensaje: String) : Exception(mensaje)

    class LibroNoPrestadoException(mensaje: String) : Exception(mensaje)

    class LimitePrestamosExcedidoException(mensaje: String) : Exception(mensaje)
}