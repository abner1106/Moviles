package com.sistemreservas.model

enum class RoomType { STANDARD, DELUXE, SUITE }

data class Room(
    val number: Int,
    val type: RoomType,
    val pricePerNight: Double,
    var available: Boolean = true
)