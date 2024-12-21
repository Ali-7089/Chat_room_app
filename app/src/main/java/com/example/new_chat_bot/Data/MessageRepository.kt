package com.example.new_chat_bot.Data

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import  com.example.new_chat_bot.Screen.Result;
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MessageRepository(
    val firestore: FirebaseFirestore
){
     suspend fun sendMessage(roomId:String,message: Message):Result<Unit> =
      try{
           firestore.collection("rooms").document(roomId).
           collection("messages").add(message).await()
          Result.Success(Unit)
      }catch(e:Exception){
           Result.Error(e)
      }
     suspend fun getMessages(roomId: String) :Flow<List<Message>> =
         callbackFlow {
             val subscription = firestore.collection("rooms").document(roomId)
                 .collection("messages")
                 .orderBy("timestamp")
                 .addSnapshotListener{ queryListner,_ ->
                     queryListner?.let {
                         trySend(it.documents.map{doc->
                             doc.toObject(Message::class.java)!!.copy()
                         }).isSuccess
                     }
                 }
             awaitClose { subscription.remove() }
         }

}