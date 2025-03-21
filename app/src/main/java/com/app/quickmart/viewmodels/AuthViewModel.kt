package com.app.quickmart.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.quickmart.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel: ViewModel() {

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    fun signUp(name: String, email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val userId = it.result?.user?.uid
                    val userModel = UserModel(name, email, userId!!)
                    firestore.collection("users").document(userId)
                        .set(userModel)
                        .addOnCompleteListener {dbTask ->
                            if(dbTask.isSuccessful) {
                                onResult(true, null)
                                Log.d("TAG", "signUp: User Stored!!!")
                            } else {
                                onResult(false, "Something went wrong!!!")
                            }
                        }
                } else {
                    onResult(false, it.exception?.localizedMessage)
                }
            }
    }
    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, it.exception?.localizedMessage)
                }
            }
    }
}