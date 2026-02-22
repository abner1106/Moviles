// Booking.kt
package com.sistemreservas.model

import java.time.LocalDate

enum class BookingStatus { ACTIVE, CANCELLED, COMPLETED }

class Booking(
    val id: String,
    val room: Room,
    val guest: Guest,
    val checkIn: LocalDate,
    val checkOut: LocalDate,
    val totalPrice: Double,
    var status: BookingStatus = BookingStatus.ACTIVE
) {
    override fun toString(): String {
        return "Booking(id='$id', room=${room.number}, guest=${guest.dni}, checkIn=$checkIn, checkOut=$checkOut, totalPrice=$totalPrice, status=$status)"
    }
}