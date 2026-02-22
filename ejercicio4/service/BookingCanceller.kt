package com.sistemreservas.service.processors

import com.sistemreservas.model.BookingStatus
import com.sistemreservas.repository.BookingRepository
import com.sistemreservas.repository.RoomRepository
import com.sistemreservas.exceptions.HotelExceptions

class BookingCanceller(
    private val bookingRepo: BookingRepository,
    private val roomRepo: RoomRepository
) {
    fun cancel(bookingId: String) {
        val booking = bookingRepo.findById(bookingId)
            ?: throw HotelExceptions.BookingNotFoundException("Reserva no encontrada")

        if (booking.status != BookingStatus.ACTIVE)
            throw HotelExceptions.InvalidCancellationException("La reserva no está activa")

        booking.status = BookingStatus.CANCELLED
        booking.room.available = true

        bookingRepo.update(booking)
        roomRepo.update(booking.room)
    }
}