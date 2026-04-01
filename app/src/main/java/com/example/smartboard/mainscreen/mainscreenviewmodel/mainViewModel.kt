package com.example.smartboard.mainscreen.mainscreenviewmodel

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.smartboard.history_feature.HistoryDataClass
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalTime

class BoardViewModel(
    private val repository: MainScreenRepository = MainScreenRepository()
) : ViewModel() {

    data class TimerData(
        val enabled: Boolean = false,
        val start: String = "00:00"
    )

    private val _timerStates = MutableStateFlow<Map<String, TimerData>>(emptyMap())
    val timerStates: StateFlow<Map<String, TimerData>> = _timerStates

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
                val deviceMap = mutableMapOf<String, Boolean>()
                val timerMap = mutableMapOf<String, TimerData>()

                snapshot.children.forEach { device ->
                    val key = device.key ?: return@forEach

                    val state = device.child("state")
                        .getValue(Boolean::class.java) ?: false

                    val timerEnabled = device.child("timer")
                        .child("enabled")
                        .getValue(Boolean::class.java) ?: false

                    val startTime = device.child("timer")
                        .child("start")
                        .getValue(String::class.java) ?: "00:00"

                    deviceMap[key] = state
                    timerMap[key] = TimerData(timerEnabled, startTime)
                }

                _deviceStates.value = deviceMap
                _timerStates.value = timerMap

                Log.d("RTDB", "Device states updated: $deviceMap")
                Log.d("RTDB", "Timer states updated: $timerMap")
            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                Log.e("RTDB", "Listener cancelled", error.toException())
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDeviceState(devicekey: String, state: Boolean, user: String = "suhelmujawar3269@gmail.com") {
        databaseref.child(devicekey).child("state").setValue(state)
        updateHistory(devicekey, if (state) "On" else "Off", user, LocalTime.now())
    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateHistory(appliance: String, operation: String, user: String, time: LocalTime) {
        val db = FirebaseFirestore.getInstance()
        db.collection(user).document("history").collection("history")
            .add(
                HistoryDataClass(
                    appliance,
                    operation,
                    user = user,
                    timestamp = LocalTime.now().toString()
                )
            )
            .addOnSuccessListener {
                Log.d("Firestore", "DocumentSnapshot successfully written!")
            }.addOnFailureListener { e ->
                Log.w("Firestore", "Error writing document", e)
            }
    }

    fun updateTimer(isTimerOn: Boolean, formattedTime: String, appliance: String) {

        val database = FirebaseDatabase.getInstance()
        val timerRef = database
            .getReference("smartboard/devices/$appliance/timer")

        val updates = mapOf(
            "enabled" to isTimerOn,
            "start" to formattedTime
        )

        timerRef.updateChildren(updates)
            .addOnSuccessListener {
                Log.d("RTDB", "Timer updated successfully")
            }
            .addOnFailureListener {
                Log.e("RTDB", "Timer update failed", it)
            }
    }
}