package com.example.smartboard.history_feature

import android.util.Log
import com.example.smartboard.history_feature.HistoryDataClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.log

class HistoryRepository {
    private val db = FirebaseFirestore.getInstance()
    private val _historyList = MutableStateFlow<List<HistoryDataClass>>(emptyList())

    val historyList: StateFlow<List<HistoryDataClass>> = _historyList

    fun fetchHistory(email: String) {
        db.collection(email).document("history").collection("history")
            .get()
            .addOnSuccessListener { result->
                val list = result.map { it.toObject(HistoryDataClass::class.java) }
                _historyList.value = list
            }        .addOnFailureListener {
                Log.e("Firestore", "Error: ", it)
            }
    }


}