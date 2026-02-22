package com.sistemreservas.repository.impl

import com.sistemreservas.model.Room
import com.sistemreservas.model.RoomType
import com.sistemreservas.repository.RoomRepository

class InMemoryRoomRepository : RoomRepository {
    private val rooms = mutableListOf<Room>()

    override fun save(room: Room) { rooms.add(room) }
    override fun findByNumber(number: Int): Room? = rooms.find { it.number == number }
    override fun findAll(): List<Room> = rooms.toList()
    override fun update(room: Room) {
        val index = rooms.indexOfFirst { it.number == room.number }
        if (index != -1) rooms[index] = room
    }
    override fun delete(number: Int): Boolean = rooms.removeIf { it.number == number }
    override fun findByType(type: RoomType): List<Room> = rooms.filter { it.type == type }
    override fun findAvailable(): List<Room> = rooms.filter { it.available }
}