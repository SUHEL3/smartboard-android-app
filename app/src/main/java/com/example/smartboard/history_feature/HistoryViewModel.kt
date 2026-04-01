package com.example.smartboard.history_feature

import androidx.lifecycle.ViewModel

class HistoryViewModel(): ViewModel() {
    private val repository: HistoryRepository = HistoryRepository()
    val historyList = repository.historyList
    fun fetchHistory(email: String ) {
        repository.fetchHistory(email)
    }
}
