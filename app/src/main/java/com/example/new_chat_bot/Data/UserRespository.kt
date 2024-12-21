package com.example.new_chat_bot.Data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.new_chat_bot.Screen.Result
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.lang.NullPointerException

class UserRespository(
    val auth: FirebaseAuth,
    val firestore: FirebaseFirestore
) {

    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<Boolean> =
        try {
            auth.createUserWithEmailAndPassword(email, password)
            val user = User(firstName, lastName, email)
            saveUserToFirestore(user)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }

    suspend fun signIn(email: String, password: String):
            Result<Boolean> = try {
               auth.signInWithEmailAndPassword(email,password)
               Result.Success(true)
            } catch (e:Exception) {
                Result.Error(e)
            }

    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }

     suspend fun getCurrentUser():Result<User> =
        try {
            val uid = auth.currentUser?.email
            if (uid!=null){
              val documentUser = firestore.collection("users").document(uid)
                     .get().await()
               val currentUser = documentUser.toObject(User::class.java)
               if (currentUser !=null){
                   Result.Success(currentUser)
               } else{
                   Result.Error(Exception("user not found"))
               }
            }else{
               Result.Error(Exception("user not authenticated"))
            }
        }catch (e:Exception){
            Result.Error(e)
        }
}