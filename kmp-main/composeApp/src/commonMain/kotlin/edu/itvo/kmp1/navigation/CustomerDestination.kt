package edu.itvo.kmp1.navigation

sealed class CustomerDestination {

    data object List : CustomerDestination()

    data object Form : CustomerDestination()
}