package edu.itvo.kmp1.feature.product.data.mapper

import edu.itvo.kmp1.feature.product.data.dto.ProductDto
import edu.itvo.kmp1.feature.product.domain.model.Product

fun Product.toDto() = ProductDto(
    id = if (id.isBlank()) null else id,
    code = if (id.isBlank()) null else id,
    description = description,
    descripcion = description,
    category = category,
    categoria = category,
    price = price,
    precio = price,
    stock = stock,
    existencia = stock,
    taxable = taxable,
    iva = taxable
)

fun ProductDto.toDomain() = Product(
    id = id ?: code ?: "",
    description = description ?: descripcion ?: "",
    category = category ?: categoria ?: "",
    price = price ?: precio ?: 0.0,
    stock = stock ?: existencia ?: 0,
    taxable = taxable ?: iva ?: true
)
