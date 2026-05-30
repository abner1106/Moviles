package edu.itvo.kmp1.feature.customer.domain.usecase

import edu.itvo.kmp1.feature.customer.domain.repository.CustomerRepository
import me.tatarka.inject.annotations.Inject

class DeleteCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
) {

    suspend operator fun invoke(id: String) {

        repository.deleteById(id)
    }
}