package com.example.smartboard.mainscreen.mainscreenviewmodel

import android.R
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainScreenRepository {

    private val db = FirebaseFirestore.getInstance()

    private val _boardList = MutableStateFlow<List<BoardItem>>(emptyList())
    val boardList: StateFlow<List<BoardItem>> = _boardList

    fun getBoard(email: String) {
        db.collection(email)
            .document("boards")
            .get()
            .addOnSuccessListener { document ->

                if (document.exists()) {

                    val list: List<BoardItem> =
                        document.data?.map { entry ->
                            BoardItem(
                                key = entry.key,
                                value = entry.value.toString()
                            )
                        } ?: emptyList()

                    _boardList.value = list
                }
            }
            .addOnFailureListener {
                Log.e("Firestore", "Error fetching data", it)
            }
    }
    fun forceTurnOFF() {
        val devicesRef = FirebaseDatabase.getInstance()
            .getReference("smartboard/devices")

        devicesRef.get().addOnSuccessListener { snapshot ->
            val updates = mutableMapOf<String, Any>()
            snapshot.children.forEach { device ->
                updates["${device.key}/state"] = false
            }
            devicesRef.updateChildren(updates)
        }
    }


}
