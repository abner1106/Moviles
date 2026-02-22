package com.sistemreservas.service.queries

import com.sistemreservas.model.Booking
import com.sistemreservas.model.BookingStatus
import com.sistemreservas.repository.impl.InMemoryGuestRepository

class BookingQueries(
    private val bookingRepo: ReadOnlyBookingRepository,
    private val guestRepo: InMemoryGuestRepository   // (análoga, la creamos a continuación)
) {
    fun findByGuest(guestDni: String): List<Booking> = bookingRepo.findByGuest(guestDni)
    fun findByRoom(roomNumber: Int): List<Booking> = bookingRepo.findByRoom(roomNumber)
    fun findActive(): List<Booking> = bookingRepo.findActive()
    fun findById(bookingId: String): Booking? = bookingRepo.findById(bookingId)

    fun getGuestStats(guestDni: String): Map<String, Any> {
        val bookings = bookingRepo.findByGuest(guestDni)
        val guest = guestRepo.findByDni(guestDni)
        return mapOf(
            "guest" to guest,
            "total" to bookings.size,
            "active" to bookings.count { it.status == BookingStatus.ACTIVE },
            "cancelled" to bookings.count { it.status == BookingStatus.CANCELLED }
        ) as Map<String, Any>
    }
}