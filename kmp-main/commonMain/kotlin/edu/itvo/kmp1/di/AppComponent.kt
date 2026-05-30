package edu.itvo.kmp1.di

import edu.itvo.kmp1.core.network.createHttpClient
import edu.itvo.kmp1.feature.customer.data.datasource.remote.CustomerRemoteDataSource
import edu.itvo.kmp1.feature.customer.data.remote.CustomerApi
import edu.itvo.kmp1.feature.customer.data.repository.CustomerRepositoryImpl
import edu.itvo.kmp1.feature.customer.data.repository.CustomerRepositoryLocalImpl
import edu.itvo.kmp1.feature.customer.domain.usecase.DeleteCustomerUseCase
import edu.itvo.kmp1.feature.customer.domain.usecase.ObserveCustomersUseCase
import edu.itvo.kmp1.feature.customer.domain.usecase.SaveCustomerUseCase
import edu.itvo.kmp1.feature.customer.presentation.viewmodel.CustomerViewModel
import edu.itvo.kmp1.feature.product.data.datasource.remote.ProductRemoteDataSource
import edu.itvo.kmp1.feature.product.data.remote.ProductApi
import edu.itvo.kmp1.feature.product.data.repository.ProductRepositoryImpl
import edu.itvo.kmp1.feature.product.data.repository.ProductRepositoryLocalImpl
import edu.itvo.kmp1.feature.product.domain.usecase.DeleteProductUseCase
import edu.itvo.kmp1.feature.product.domain.usecase.ObserveProductsUseCase
import edu.itvo.kmp1.feature.product.domain.usecase.SaveProductUseCase
import edu.itvo.kmp1.feature.product.presentation.viewmodel.ProductViewModel

class AppComponent {

    private val httpClient = createHttpClient()
    private val baseUrl = "http://192.168.1.68:3003"

    // Customers
    private val customerApi = CustomerApi(
        client = httpClient,
        baseUrl = baseUrl
    )
    private val customerRemoteDataSource = CustomerRemoteDataSource(customerApi)
    private val customerLocalRepository = CustomerRepositoryLocalImpl()
    
    // El repositorio ahora usa tanto el remoto como el local para persistencia doble
    private val customerRepository = CustomerRepositoryImpl(
        remote = customerRemoteDataSource,
        local = customerLocalRepository
    )

    private val observeCustomersUseCase = ObserveCustomersUseCase(customerRepository)
    private val saveCustomerUseCase = SaveCustomerUseCase(customerRepository)
    private val deleteCustomerUseCase = DeleteCustomerUseCase(customerRepository)

    val customerViewModel = CustomerViewModel(
        observeCustomersUseCase,
        saveCustomerUseCase,
        deleteCustomerUseCase
    )

    // Products
    private val productApi = ProductApi(
        client = httpClient,
        baseUrl = baseUrl
    )
    private val productRemoteDataSource = ProductRemoteDataSource(productApi)
    private val productLocalRepository = ProductRepositoryLocalImpl()
    private val productRepository = ProductRepositoryImpl(
        remote = productRemoteDataSource,
        local = productLocalRepository
    )

    private val observeProductsUseCase = ObserveProductsUseCase(productRepository)
    private val saveProductUseCase = SaveProductUseCase(productRepository)
    private val deleteProductUseCase = DeleteProductUseCase(productRepository)

    val productViewModel = ProductViewModel(
        observeProductsUseCase,
        saveProductUseCase,
        deleteProductUseCase
    )
}
