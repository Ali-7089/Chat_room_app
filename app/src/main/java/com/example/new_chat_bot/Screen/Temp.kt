package com.example.new_chat_bot.Screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun temp(){
    val now = LocalDateTime.now()
    println(now)
    val messegedDate =
           LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
               ZoneId.systemDefault())
    when{
        isSameDay(messegedDate,now) -> "Today ${timeFormat(messegedDate)}"
        isSameDay(messegedDate.plusDays(1),now) -> "Yesterday ${messegedDate}"
        else -> dateFormat(messegedDate)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isSameDay(LocalDate1:LocalDateTime, LocalDate2: LocalDateTime):Boolean{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    println(LocalDate1.format(formatter))
    println(LocalDate2.format(formatter))
    return LocalDate1.format(formatter) == LocalDate2.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
private fun dateFormat(LocalDate: LocalDateTime):String{
     val formatter = DateTimeFormatter.ofPattern("MMM,dd yyyy")
     println(formatter.format(LocalDate))
     return formatter.format(LocalDate)
}
@RequiresApi(Build.VERSION_CODES.O)
private fun timeFormat(LocalDate: LocalDateTime):String{
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    println(formatter.format(LocalDate))
    return formatter.format(LocalDate)
}
