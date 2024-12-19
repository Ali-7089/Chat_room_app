package com.example.new_chat_bot.Screen

import com.example.new_chat_bot.Data.Room

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}