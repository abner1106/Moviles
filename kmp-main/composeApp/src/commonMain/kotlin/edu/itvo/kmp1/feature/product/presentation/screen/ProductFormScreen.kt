package edu.itvo.kmp1.feature.product.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.itvo.kmp1.feature.product.domain.model.Product
import edu.itvo.kmp1.feature.product.presentation.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    viewModel: ProductViewModel,
    productId: String? = null,
    onBack: () -> Unit
) {
    val products by viewModel.products.collectAsState()
    val productToEdit = remember(productId, products) {
        products.find { it.id == productId }
    }

    var code by remember { mutableStateOf(productToEdit?.id ?: "") }
    var description by remember { mutableStateOf(productToEdit?.description ?: "") }
    var category by remember { mutableStateOf(productToEdit?.category ?: "") }
    var price by remember { mutableStateOf(productToEdit?.price?.toString() ?: "") }
    var stock by remember { mutableStateOf(productToEdit?.stock?.toString() ?: "") }
    var taxable by remember { mutableStateOf(productToEdit?.taxable ?: true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (productId == null) "Nuevo Producto" else "Editar Producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("⬅️")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Código") },
                modifier = Modifier.fillMaxWidth(),
                enabled = productId == null
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Gravable (Impuestos)")
                Switch(checked = taxable, onCheckedChange = { taxable = it })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val product = Product(
                        id = code,
                        description = description,
                        category = category,
                        price = price.toDoubleOrNull() ?: 0.0,
                        stock = stock.toIntOrNull() ?: 0,
                        taxable = taxable
                    )
                    viewModel.saveProduct(
                        product,
                        isUpdate = TODO()
                    )
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}
