package edu.itvo.kmp1.feature.customer.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.itvo.kmp1.feature.customer.presentation.component.CustomerItemCard
import edu.itvo.kmp1.feature.customer.presentation.event.CustomerEvent
import edu.itvo.kmp1.feature.customer.presentation.viewmodel.CustomerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerListScreen(
    viewModel: CustomerViewModel,
    onAddClick: () -> Unit
) {

    val customers by viewModel.customers.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listado de Clientes") }

            )

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Text("➕")
            }
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(customers) { customer ->

                CustomerItemCard(
                    customer = customer,
                    onDeleteClick = {
                        viewModel.onEvent(
                            CustomerEvent.DeleteCustomer(customer.id)
                        )
                    }
                )
            }
        }
    }
}