package com.example.new_chat_bot.Data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.new_chat_bot.Screen.Result
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
}