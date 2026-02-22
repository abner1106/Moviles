package com.sistemreservas.repository

import com.sistemreservas.model.Room
import com.sistemreservas.model.RoomType

interface RoomRepository {
    fun save(room: Room)
    fun findByNumber(number: Int): Room?
    fun findAll(): List<Room>
    fun update(room: Room)
    fun delete(number: Int): Boolean
    fun findByType(type: RoomType): List<Room>
    fun findAvailable(): List<Room>
}