package edu.itvo.kmp1.feature.customer.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class CustomerDto(
    @SerialName("id") val id: String? = null,
    @SerialName("code") val code: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("nombre") val nombre: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("correo") val correo: String? = null
)
