package com.example.smartboard.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object ForgotPassword : Screen("forgotpassword")
    object CreateAccount : Screen("createaccount")
    object Main : Screen("main")

    object History:Screen(route = "mainscreen")
}
