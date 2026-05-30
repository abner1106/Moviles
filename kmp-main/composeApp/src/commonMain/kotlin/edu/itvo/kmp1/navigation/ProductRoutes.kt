package edu.itvo.kmp1.navigation

sealed class ProductRoutes(val route: String) {
    data object List : ProductRoutes("product/list")
    data object Form : ProductRoutes("product/form")
}
