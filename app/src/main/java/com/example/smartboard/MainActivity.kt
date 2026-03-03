package com.example.smartboard

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.smartboard.navigation.SetupNavGraph
import com.example.smartboard.ui.theme.SmartBoardTheme
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            SmartBoardTheme {

                Firebase.firestore.firestoreSettings =
                    FirebaseFirestoreSettings.Builder()
                        .setPersistenceEnabled(true)
                        .build()

                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}


