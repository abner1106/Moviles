package edu.itvo.kmp1.feature.customer.domain.usecase


import edu.itvo.kmp1.feature.customer.domain.repository.CustomerRepository
import me.tatarka.inject.annotations.Inject

class ObserveCustomersUseCase @Inject constructor(
    private val repository: CustomerRepository
) {

    operator fun invoke() =
        repository.observeAll()
}