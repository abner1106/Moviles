package com.sistemreservas.exceptions

sealed class HotelException(message: String) : Exception(message)

object HotelExceptions {
    class RoomNotFoundException(message: String) : HotelException(message)
    class GuestNotFoundException(message: String) : HotelException(message)
    class BookingNotFoundException(message: String) : HotelException(message)
    class RoomNotAvailableException(message: String) : HotelException(message)
    class InvalidDateRangeException(message: String) : HotelException(message)
    class InvalidCancellationException(message: String) : HotelException(message)
}