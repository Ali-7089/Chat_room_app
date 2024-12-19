package com.example.new_chat_bot.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_chat_bot.Data.UserRespository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import com.example.new_chat_bot.Screen.Result

class AuthViewModel:ViewModel(){
    val userRespository:UserRespository
    init {
        userRespository = UserRespository(
            FirebaseAuth.getInstance(),
            FirebaseFirestore.getInstance()
        )
    }

    private val _result=MutableLiveData<Result<Boolean>>()
    val result :LiveData<Result<Boolean>> = _result

    fun signUp(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            _result.value = userRespository.signUp(email, password, firstName, lastName)
        }
    }
    fun signIn(email:String,password: String){
         viewModelScope.launch {
             _result.value = userRespository.signIn(email, password)
         }
    }
}


