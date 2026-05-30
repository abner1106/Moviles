package edu.itvo.kmp1.feature.product.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String = "",
    val description: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val taxable: Boolean = true
)
