package com.sistemreservas.repository.impl

import com.sistemreservas.model.Booking
import com.sistemreservas.model.BookingStatus
import com.sistemreservas.repository.BookingRepository
import com.sistemreservas.service.queries.ReadOnlyBookingRepository
import java.time.LocalDate

class InMemoryBookingRepository : BookingRepository, ReadOnlyBookingRepository {
    private val bookings = mutableListOf<Booking>()

    override fun save(booking: Booking) { bookings.add(booking) }
    override fun findById(id: String): Booking? = bookings.find { it.id == id }
    override fun findAll(): List<Booking> = bookings.toList()
    override fun findByGuest(dni: String): List<Booking> = bookings.filter { it.guest.dni == dni }
    override fun findByRoom(roomNumber: Int): List<Booking> = bookings.filter { it.room.number == roomNumber }
    override fun findActive(): List<Booking> = bookings.filter { it.status == BookingStatus.ACTIVE }
    override fun findOverlapping(roomNumber: Int, checkIn: LocalDate, checkOut: LocalDate): List<Booking> =
        bookings.filter { booking ->
            booking.room.number == roomNumber &&
                    booking.status == BookingStatus.ACTIVE &&
                    !(checkOut.isBefore(booking.checkIn) || checkIn.isAfter(booking.checkOut))
        }
    override fun update(booking: Booking) {
        val index = bookings.indexOfFirst { it.id == booking.id }
        if (index != -1) bookings[index] = booking
    }
    override fun delete(id: String): Boolean = bookings.removeIf { it.id == id }
}