package edu.itvo.kmp1.feature.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.itvo.kmp1.feature.product.domain.model.Product
import edu.itvo.kmp1.feature.product.domain.usecase.DeleteProductUseCase
import edu.itvo.kmp1.feature.product.domain.usecase.ObserveProductsUseCase
import edu.itvo.kmp1.feature.product.domain.usecase.SaveProductUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(
    private val observeProductsUseCase: ObserveProductsUseCase,
    private val saveProductUseCase: SaveProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    val products: StateFlow<List<Product>> = observeProductsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun saveProduct(product: Product, isUpdate: Boolean) {
        viewModelScope.launch {
            saveProductUseCase(product, isUpdate)
        }
    }

    fun deleteProduct(id: String) {
        viewModelScope.launch {
            deleteProductUseCase(id)
        }
    }
}
