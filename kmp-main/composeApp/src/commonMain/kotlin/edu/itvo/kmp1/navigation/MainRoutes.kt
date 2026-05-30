package edu.itvo.kmp1.navigation

sealed class MainRoutes(val route: String) {
    data object Home : MainRoutes("home")
    data object Customers : MainRoutes("customers_section")
    data object Products : MainRoutes("products_section")
}
