package com.example.tasko.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasko.data.Repository
import com.example.tasko.data.retrofit.models.MessageResponse
import com.example.tasko.data.retrofit.models.User
import com.example.tasko.data.retrofit.models.UserRequest
import com.example.tasko.screens.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: Repository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateUser())
    val uiState: StateFlow<UiStateUser> = _uiState

    val usernameFlow: Flow<String?> = userPreferences.usernameFlow


    fun fetchLogin(username: String, password: String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)

        try {
            val request = UserRequest(username, password)
            val response = repository.logIn(request)

            Log.d("LOGIN", response.toString())



            val loggedIn = response.message.equals("TRUE", ignoreCase = true)

            if (loggedIn) {
                // Sačuvaj username i password
                userPreferences.saveUser(username, password)
            }

            _uiState.value = UiStateUser(
                user = response,
                isRefreshing = false,
                isLoggedIn = loggedIn
            )

        } catch (e: Exception) {
            _uiState.value = UiStateUser(
                user = null,
                isRefreshing = false,
                error = e.localizedMessage,
                isLoggedIn = false
            )
        }
    }

}

data class UiStateUser(
    val user: MessageResponse? = null,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)