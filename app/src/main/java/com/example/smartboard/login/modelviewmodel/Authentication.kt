package com.example.smartboard.login.modelviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        repository.login(email, password) { success, message ->
            _authState.value =
                if (success) AuthState.Success(message)
                else AuthState.Error(message)
        }

    }

    fun register(email: String, password: String) {
        _authState.value = AuthState.Loading
        repository.register(email, password) { success, message ->
            _authState.value =
                if (success) AuthState.Success(message)
                else AuthState.Error(message)
        }
    }

    fun logout() {
        repository.logout()
        _authState.value = AuthState.Idle
    }
}