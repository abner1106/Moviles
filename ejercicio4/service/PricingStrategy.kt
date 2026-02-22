package com.sistemreservas.service.calculators

import com.sistemreservas.model.Room

interface PricingStrategy {
    fun calculate(room: Room, nights: Long): Double
}