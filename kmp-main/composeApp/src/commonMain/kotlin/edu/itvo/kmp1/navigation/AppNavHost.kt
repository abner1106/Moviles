package edu.itvo.kmp1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.itvo.kmp1.feature.customer.presentation.screen.CustomerFormScreen
import edu.itvo.kmp1.feature.customer.presentation.screen.CustomerListScreen
import edu.itvo.kmp1.feature.customer.presentation.viewmodel.CustomerViewModel
import edu.itvo.kmp1.feature.product.presentation.screen.ProductFormScreen
import edu.itvo.kmp1.feature.product.presentation.screen.ProductListScreen
import edu.itvo.kmp1.feature.product.presentation.viewmodel.ProductViewModel
import edu.itvo.kmp1.presentation.screen.HomeScreen


@Composable
fun AppNavHost(
    customerViewModel: CustomerViewModel,
    productViewModel: ProductViewModel
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainRoutes.Home.route
    ) {
        
        composable(MainRoutes.Home.route) {
            HomeScreen(
                onNavigateToCustomers = {
                    navController.navigate(CustomerRoutes.List.route)
                },
                onNavigateToProducts = {
                    navController.navigate(ProductRoutes.List.route)
                }
            )
        }

        // Customers
        composable(CustomerRoutes.List.route) {
            CustomerListScreen(
                viewModel = customerViewModel,
                onAddClick = {
                    navController.navigate(CustomerRoutes.Form.route)
                }
            )
        }

        composable(CustomerRoutes.Form.route) {
            CustomerFormScreen(
                viewModel = customerViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Products
        composable(ProductRoutes.List.route) {
            ProductListScreen(
                viewModel = productViewModel,
                onAddClick = {
                    navController.navigate(ProductRoutes.Form.route)
                },
                onEditClick = { id ->
                    navController.navigate("${ProductRoutes.Form.route}/$id")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "${ProductRoutes.Form.route}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.savedStateHandle.get<String>("productId")
            ProductFormScreen(
                viewModel = productViewModel,
                productId = productId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(ProductRoutes.Form.route) {
            ProductFormScreen(
                viewModel = productViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
