package com.sistemaeventos.repository.impl

import com.sistemaeventos.model.Event
import com.sistemaeventos.repository.EventRepository

class InMemoryEventRepository : EventRepository {
    private val events = mutableListOf<Event>()

    override fun save(event: Event) { events.add(event) }
    override fun findById(id: String): Event? = events.find { it.id == id }
    override fun findAll(): List<Event> = events.toList()
    override fun update(event: Event) {
        val index = events.indexOfFirst { it.id == event.id }
        if (index != -1) events[index] = event
    }
    override fun delete(id: String): Boolean = events.removeIf { it.id == id }
}