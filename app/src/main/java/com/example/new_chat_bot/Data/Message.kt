package com.example.new_chat_bot.Data

data class Message(
    val senderFirstName :String = "",
    val senderId :String="",
    val text:String ="",
    val timeStamp:Long = System.currentTimeMillis(),
    val isCurrentUserSent :Boolean = false
)
