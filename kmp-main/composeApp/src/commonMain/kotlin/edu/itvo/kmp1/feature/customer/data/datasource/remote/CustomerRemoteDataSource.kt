package edu.itvo.kmp1.feature.customer.data.datasource.remote


import edu.itvo.kmp1.feature.customer.data.dto.CustomerDto
import edu.itvo.kmp1.feature.customer.data.remote.CustomerApi

class CustomerRemoteDataSource(
    private val api: CustomerApi
) {

    suspend fun getCustomers(): List<CustomerDto> {

        return api.getCustomers()
    }

    suspend fun saveCustomer(
        customer: CustomerDto,
        isUpdate: Boolean
    ) {

        api.saveCustomer(customer, isUpdate)
    }

    suspend fun deleteCustomer(
        id: String
    ) {

        api.deleteCustomer(id)
    }
}
