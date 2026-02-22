package com.sistemaeventos.repository

import com.sistemaeventos.model.Attendee

interface AttendeeRepository {
    fun save(attendee: Attendee)
    fun findById(id: String): Attendee?
    fun findAll(): List<Attendee>
    fun update(attendee: Attendee)
    fun delete(id: String): Boolean
    fun findByEmail(email: String): Attendee?
}