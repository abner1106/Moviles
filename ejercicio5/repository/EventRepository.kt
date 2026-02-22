package com.sistemaeventos.repository

import com.sistemaeventos.model.Event

interface EventRepository {
    fun save(event: Event)
    fun findById(id: String): Event?
    fun findAll(): List<Event>
    fun update(event: Event)
    fun delete(id: String): Boolean
}