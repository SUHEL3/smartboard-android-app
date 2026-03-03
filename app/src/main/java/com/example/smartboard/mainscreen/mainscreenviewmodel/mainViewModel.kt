package com.example.smartboard.mainscreen.mainscreenviewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.plus

class BoardViewModel(
    private val repository: MainScreenRepository = MainScreenRepository()
) : ViewModel() {
    fun getBoard(email: String) {
        repository.getBoard(email)
    }

    val boardList = repository.boardList

    fun forceTurnOFF() {
        repository.forceTurnOFF()
    }

    private val databaseref = FirebaseDatabase.getInstance()
        .getReference("smartboard")
        .child("devices")

    private val _deviceStates =
        MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val deviceStates: StateFlow<Map<String, Boolean>> = _deviceStates


    fun listenToDeviceStates() {
        databaseref.addValueEventListener(object : com.google.firebase.database.ValueEventListener {

            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                val newMap = mutableMapOf<String, Boolean>()

                snapshot.children.forEach { device ->
                    val key = device.key ?: return@forEach
                    val state = device.child("state")
                        .getValue(Boolean::class.java) ?: false

                    newMap[key] = state
                }

                _deviceStates.value = newMap.toMap()
                Log.d("RTDB", "Device states updated: $newMap")
            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                Log.e("RTDB", "Listener cancelled", error.toException())
            }
        })
    }

    fun updateDeviceState(devicekey: String, state: Boolean) {
        Log.d("STEP1", "Updating $devicekey to $state")
        databaseref.child(devicekey).child("state").setValue(state)
    }
    private val db = FirebaseFirestore.getInstance()
    private val _historyList = MutableStateFlow<List<HistoryDataClass>>(emptyList())
    val historyList: StateFlow<List<HistoryDataClass>> = _historyList
    fun FetchHistory(email : String = "suhelmujawar3269@gmail.com"){
        db.collection(email).document("history").collection("history")
            .get().addOnSuccessListener {snapshot->
                val list = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(HistoryDataClass::class.java)
                }
                _historyList.value = list
            }
    }
}


