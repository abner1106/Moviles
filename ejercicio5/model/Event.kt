package com.sistemaeventos.model

import java.time.LocalDate

data class Event(
    val id: String,
    val name: String,
    val date: LocalDate,
    val activities: List<String> // IDs de actividades
)