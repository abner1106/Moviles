package edu.itvo.kmp1.feature.customer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.itvo.kmp1.feature.customer.domain.model.Customer
import edu.itvo.kmp1.feature.customer.domain.usecase.DeleteCustomerUseCase
import edu.itvo.kmp1.feature.customer.domain.usecase.ObserveCustomersUseCase
import edu.itvo.kmp1.feature.customer.domain.usecase.SaveCustomerUseCase
import edu.itvo.kmp1.feature.customer.presentation.event.CustomerEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CustomerViewModel(
    private val observeCustomersUseCase: ObserveCustomersUseCase,
    private val saveCustomerUseCase: SaveCustomerUseCase,
    private val deleteCustomerUseCase: DeleteCustomerUseCase
) : ViewModel() {

    val customers: StateFlow<List<Customer>> = observeCustomersUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onEvent(event: CustomerEvent) {
        when (event) {
            is CustomerEvent.SaveCustomer -> {
                viewModelScope.launch {
                    saveCustomerUseCase(event.customer)
                }
            }
            is CustomerEvent.DeleteCustomer -> {
                viewModelScope.launch {
                    deleteCustomerUseCase(event.id)
                }
            }
        }
    }
}
