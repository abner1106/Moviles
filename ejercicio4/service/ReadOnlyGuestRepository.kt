package com.sistemreservas.service.queries

import com.sistemreservas.model.Guest

interface ReadOnlyGuestRepository {
    fun findByDni(dni: String): Guest?
    fun findAll(): List<Guest>
}