package com.example.new_chat_bot.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_chat_bot.Data.Room
import com.example.new_chat_bot.Data.RoomRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import com.example.new_chat_bot.Screen.Result

class RoomViewModel():ViewModel(){
    private val _rooms = MutableLiveData<List<Room>>()
    val room :LiveData<List<Room>> get()  = _rooms

    val roomRepository :RoomRepository
    init {
       roomRepository = RoomRepository(
           FirebaseFirestore.getInstance()
       )
        getRoom()
    }

    fun createRoom(name:String){
        val room = Room(room_name = name)
        viewModelScope.launch {
            roomRepository.createRoom(room)
        }
    }
    fun getRoom(){
        viewModelScope.launch {
            when (val result = roomRepository.getRooms()) {
                is Result.Success -> _rooms.value = result.data
                is Result.Error -> {

                }
            }
        }
    }
}