package com.sistemaeventos.model

import java.time.LocalTime

data class Activity(
    val id: String,
    val name: String,
    val speaker: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val maxCapacity: Int
)