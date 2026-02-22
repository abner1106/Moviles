package com.sistemaeventos.service.queries

import com.sistemaeventos.model.Activity

interface ReadOnlyActivityRepository {
    fun findById(id: String): Activity?
    fun findAll(): List<Activity>
    fun findByEvent(eventId: String): List<Activity>
}