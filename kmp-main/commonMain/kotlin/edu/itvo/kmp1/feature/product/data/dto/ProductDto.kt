package edu.itvo.kmp1.feature.product.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ProductDto(
    @SerialName("id") val id: String? = null,
    @SerialName("code") val code: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("descripcion") val descripcion: String? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("categoria") val categoria: String? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("precio") val precio: Double? = null,
    @SerialName("stock") val stock: Int? = null,
    @SerialName("existencia") val existencia: Int? = null,
    @SerialName("taxable") val taxable: Boolean? = null,
    @SerialName("iva") val iva: Boolean? = null
)
