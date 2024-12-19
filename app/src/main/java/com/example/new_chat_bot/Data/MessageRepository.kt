package com.example.new_chat_bot.Data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import  com.example.new_chat_bot.Screen.Result;

class MessageRepository(
    val firestore: FirebaseFirestore
){
    suspend fun createMessage(room_id:String,message:Message):Result<Unit> =
        try{
        firestore.collection("rooms").document(room_id)
            .collection("messages").add(message).await()
        Result.Success(Unit)
        }catch(e:Exception){
             Result.Error(e)
        }
}