// Guest.kt
package com.sistemreservas.model

class Guest(
    val dni: String,
    val name: String,
    val email: String,
    val bookingHistory: MutableList<Booking> = mutableListOf()
) {
    override fun toString(): String {
        return "Guest(dni='$dni', name='$name', email='$email', bookingHistory.size=${bookingHistory.size})"
    }
}