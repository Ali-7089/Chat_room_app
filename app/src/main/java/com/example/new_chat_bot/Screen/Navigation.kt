package com.example.new_chat_bot.Screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.new_chat_bot.Model.AuthViewModel

@Composable
fun navigation(){
    var navController = rememberNavController();
    val authViewModel:AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.SignUp.name) {
        composable(Screen.SignUp.name){
            SignUpScreen(authViewModel){
                navController.navigate(Screen.SignIn.name)
            }
        }
        composable(Screen.SignIn.name){
            LoginScreen(authViewModel){
                navController.navigate(Screen.SignIn.name)
            }
        }
    }
}