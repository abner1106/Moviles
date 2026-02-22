package com.sistemreservas.service.validators

import com.sistemreservas.exceptions.HotelExceptions
import com.sistemreservas.model.Guest

class GuestValidator {
    fun validateExists(guest: Guest?) {
        if (guest == null) throw HotelExceptions.GuestNotFoundException("Huésped no encontrado")
    }
}