package com.sistemreservas.repository

import com.sistemreservas.model.Guest

interface GuestRepository {
    fun save(guest: Guest)
    fun findByDni(dni: String): Guest?
    fun findAll(): List<Guest>
    fun update(guest: Guest)
    fun delete(dni: String): Boolean
}