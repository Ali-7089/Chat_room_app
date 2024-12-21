package com.example.new_chat_bot.Screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.new_chat_bot.Model.AuthViewModel
import com.example.new_chat_bot.Model.MessageViewModel
import com.example.new_chat_bot.Model.RoomViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun navigation(){
    var navController = rememberNavController();
    val authViewModel:AuthViewModel = viewModel()
    val roomViewModel:RoomViewModel= viewModel()
    val messageViewModel:MessageViewModel = viewModel()


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
        composable(Screen.ChatRoomList.name){
            chatRoomListScreen(roomViewModel){
                 navController.navigate("${Screen.ChatScreen.name}/${it.room_id}")
            }
        }
        composable("${Screen.ChatScreen.name}/{room_id}"){
                val id = it.arguments?.get("room_id").toString()?:""
                ChatScreen(roomId = id,messageViewModel)
        }
    }
}