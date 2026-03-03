package com.example.smartboard.login.modelviewmodel
import com.google.firebase.auth.FirebaseAuth
class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(true, "Login Successful")
                } else {
                    onResult(false, it.exception?.message ?: "Login Failed")
                }
            }
    }

    fun register(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(true, "Registration Successful")
                } else {
                    onResult(false, it.exception?.message ?: "Registration Failed")
                }
            }
    }

    fun logout() {
        auth.signOut()
    }

 //   fun isLoggedIn(): Boolean = auth.currentUser != null
}