package com.example.new_chat_bot.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.new_chat_bot.Data.Message
import com.example.new_chat_bot.Data.MessageRepository
import com.example.new_chat_bot.Data.User
import com.example.new_chat_bot.Data.UserRespository
import com.example.new_chat_bot.Screen.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MessageViewModel:ViewModel(){
     val messageRepository:MessageRepository
     val userRespository:UserRespository
     init {
         messageRepository = MessageRepository(FirebaseFirestore.getInstance())
         userRespository = UserRespository(
             FirebaseAuth.getInstance(),
             FirebaseFirestore.getInstance()
         )
         loadCurrentUser()
     }

    private val _messages = MutableLiveData<List<Message>>()
    val messages :LiveData<List<Message>> = _messages

    private val _roomId = MutableLiveData<String>()

    private val _currentUser = MutableLiveData<User>()
    val currentUser:LiveData<User> = _currentUser

    fun setRoomId(room_id :String){
       _roomId.value = room_id
        loadMessages()
    }
    fun loadMessages(){
       viewModelScope.launch {
              messageRepository.getMessages(_roomId.value.toString())
                  .collect{_messages.value = it}
       }
    }
    fun sendMessage(text:String){
         val message = Message(
             senderFirstName =  _currentUser.value!!.firstName,
             senderId = _currentUser.value!!.email,
             text = text
         )
        viewModelScope.launch {
            messageRepository.sendMessage(
                _roomId.value.toString(),message
            )
        }
    }
    fun loadCurrentUser(){
           viewModelScope.launch {
              when(val user = userRespository.getCurrentUser()){
                  is Result.Success -> _currentUser.value = user.data
                  is Result.Error ->{

                  }
              }
           }
    }
}