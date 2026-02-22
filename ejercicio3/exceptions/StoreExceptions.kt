package ejercicio3.tienda.exceptions

sealed class StoreException(message: String) : Exception(message)

object StoreExceptions {
    class ProductoNoEncontradoException(message: String) : StoreException(message)
    class ClienteNoEncontradoException(message: String) : StoreException(message)
    class StockInsuficienteException(message: String) : StoreException(message)
    class CarritoVacioException(message: String) : StoreException(message)
    class CantidadInvalidaException(message: String) : StoreException(message)
}