package edu.itvo.kmp1.feature.customer.data.mapper

import edu.itvo.kmp1.feature.customer.data.dto.CustomerDto
import edu.itvo.kmp1.feature.customer.domain.model.Customer

fun CustomerDto.toDomain(): Customer {
    return Customer(
        id = id ?: code ?: "",
        name = name ?: nombre ?: "",
        email = email ?: correo ?: ""
    )
}

fun Customer.toDto(): CustomerDto {
    return CustomerDto(
        id = if (id.isBlank()) null else id,
        code = if (id.isBlank()) null else id,
        name = name,
        nombre = name,
        email = email,
        correo = email
    )
}
