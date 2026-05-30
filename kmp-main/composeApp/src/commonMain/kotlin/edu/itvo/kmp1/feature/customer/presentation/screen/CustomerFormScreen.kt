package edu.itvo.kmp1.feature.customer.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.itvo.kmp1.feature.customer.domain.model.Customer
import edu.itvo.kmp1.feature.customer.presentation.component.CustomerFormCard
import edu.itvo.kmp1.feature.customer.presentation.event.CustomerEvent
import edu.itvo.kmp1.feature.customer.presentation.viewmodel.CustomerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerFormScreen(
    viewModel: CustomerViewModel,
    onBack: () -> Unit
) {

    var id by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Customer Form")
                },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    )  { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            CustomerFormCard(
                id = id,
                name = name,
                email = email,
                onIdChange = { id = it },
                onNameChange = { name = it },
                onEmailChange = { email = it },
                onSaveClick = {

                    viewModel.onEvent(
                        CustomerEvent.SaveCustomer(
                            Customer(
                                id = id,
                                name = name,
                                email = email
                            )

                        )
                    )

                    id = ""
                    name = ""
                    email = ""

                    onBack()
                }
            )
        }
    }
}