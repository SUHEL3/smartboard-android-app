package com.example.smartboard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartboard.login.CreateAccountScreen
import com.example.smartboard.login.ForgotPasswordScreen
import com.example.smartboard.login.LoginScreen
import com.example.smartboard.mainscreen.MainScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
){
    val navController = rememberNavController()
    NavHost(
        navController = navController ,
        startDestination = Screen.Main.route
    ) {
        //login
        composable(route = Screen.Login.route) {
            LoginScreen({
                navController.navigate(Screen.ForgotPassword.route)
            }, {
                navController.navigate(Screen.CreateAccount.route)
            },
                navController = navController)
        }
        //forgot password
        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen({
                navController.popBackStack()
            })
        }
        //main screen
       composable(route = Screen.Main.route){
             MainScreen()
       }
        composable(route = Screen.CreateAccount.route) {
            CreateAccountScreen({
                navController.popBackStack()
            })
        }
        composable(route = Screen.History.route){
            //HistoryScreen()
        }
    }
    }