package com.example.new_chat_bot.Screen

sealed class Screen(val name:String){
    object SignUp: Screen("sign-up")
    object SignIn: Screen("sign-in")
    object ChatRoomList : Screen("chat-room-list")
    object ChatScreen : Screen("chat-screen")
}