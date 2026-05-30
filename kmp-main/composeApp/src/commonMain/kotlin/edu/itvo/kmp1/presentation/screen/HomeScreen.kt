package edu.itvo.kmp1.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCustomers: () -> Unit,
    onNavigateToProducts: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Sistema de Ventas") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onNavigateToCustomers,
                modifier = Modifier.fillMaxWidth(0.8f).padding(8.dp)
            ) {
                Text("👥 Gestionar Clientes")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNavigateToProducts,
                modifier = Modifier.fillMaxWidth(0.8f).padding(8.dp)
            ) {
                Text("📦 Gestionar Productos")
            }
        }
    }
}
