package com.sistemreservas.repository.impl

import com.sistemreservas.model.Guest
import com.sistemreservas.repository.GuestRepository

class InMemoryGuestRepository : GuestRepository {
    private val guests = mutableListOf<Guest>()

    override fun save(guest: Guest) { guests.add(guest) }
    override fun findByDni(dni: String): Guest? = guests.find { it.dni == dni }
    override fun findAll(): List<Guest> = guests.toList()
    override fun update(guest: Guest) {
        val index = guests.indexOfFirst { it.dni == guest.dni }
        if (index != -1) guests[index] = guest
    }
    override fun delete(dni: String): Boolean = guests.removeIf { it.dni == dni }
}