package com.sistemreservas.service.calculators

import com.sistemreservas.model.Room

class PriceCalculator(
    private val strategy: PricingStrategy = StandardPricing()   // inyección de estrategia
) {
    fun calculate(room: Room, nights: Long): Double = strategy.calculate(room, nights)
}