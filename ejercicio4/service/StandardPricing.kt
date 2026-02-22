package com.sistemreservas.service.calculators

import com.sistemreservas.model.Room
import com.sistemreservas.model.RoomType

class StandardPricing : PricingStrategy {
    override fun calculate(room: Room, nights: Long): Double {
        val multiplier = when (room.type) {
            RoomType.STANDARD -> 1.0
            RoomType.DELUXE -> 1.3
            RoomType.SUITE -> 1.8
        }
        return room.pricePerNight * nights * multiplier
    }
}