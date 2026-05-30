package edu.itvo.kmp1

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import edu.itvo.kmp1.di.AppComponent
import edu.itvo.kmp1.navigation.AppNavHost

@Composable
fun App() {

    val component = remember {
        AppComponent()
    }

    MaterialTheme {
        AppNavHost(
            customerViewModel = component.customerViewModel,
            productViewModel = component.productViewModel
        )
    }
}
