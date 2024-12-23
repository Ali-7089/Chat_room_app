package com.example.new_chat_bot.Data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import  com.example.new_chat_bot.Screen.Result;

class RoomRepository(
    val firestore: FirebaseFirestore
){
    suspend fun createRoom(room: Room):Result<Unit> =
        try {
          firestore.collection("rooms")
              .add(room).await()
            Result.Success(Unit)
        }
        catch (e:Exception){
            Result.Error(e)
        }

    suspend fun getRooms():Result<List<Room>> =
        try {
            val querySnapshot = firestore.collection("rooms").get().await()
            val rooms = querySnapshot.documents.map { document ->
                document.toObject(Room::class.java)!!.copy(room_id = document.id)
            }
           Result.Success(rooms)
        }catch (e:Exception){
            Result.Error(e)
        }
}