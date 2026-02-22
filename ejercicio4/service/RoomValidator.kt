package com.sistemreservas.service.validators

import com.sistemreservas.exceptions.HotelExceptions
import com.sistemreservas.model.Room

class RoomValidator {
    fun validateExists(room: Room?) {
        if (room == null) throw HotelExceptions.RoomNotFoundException("Habitación no encontrada")
    }

    fun validateAvailable(room: Room) {
        if (!room.available) throw HotelExceptions.RoomNotAvailableException("Habitación no disponible")
    }
}