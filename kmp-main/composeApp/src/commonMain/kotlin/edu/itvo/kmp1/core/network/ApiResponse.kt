package edu.itvo.kmp1.core.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T> (
    val success: Boolean = true,
    val message: String? = null,
    val data: T? = null
)
