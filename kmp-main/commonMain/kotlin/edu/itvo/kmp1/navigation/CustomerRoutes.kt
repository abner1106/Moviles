package edu.itvo.kmp1.navigation


sealed class CustomerRoutes(val route: String) {

    data object List : CustomerRoutes("customer/list")

    data object Form : CustomerRoutes("customer/form")
}