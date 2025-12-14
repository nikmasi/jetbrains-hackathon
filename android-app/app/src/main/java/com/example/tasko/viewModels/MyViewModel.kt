package com.example.tasko.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasko.data.Repository
import com.example.tasko.data.retrofit.models.MessageResponse
import com.example.tasko.data.retrofit.models.NewProject
import com.example.tasko.data.retrofit.models.ProjectList
import com.example.tasko.data.retrofit.models.TaskName
import com.example.tasko.data.retrofit.models.TasksListList
import com.example.tasko.data.retrofit.models.User
import com.example.tasko.data.retrofit.models.UserRequest
import com.example.tasko.screens.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
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

    private val _uiStateProjectsList = MutableStateFlow(UiStateListProjects())
    val uiStateProjectsList: StateFlow<UiStateListProjects> = _uiStateProjectsList

    private val _uiStateTasksListList = MutableStateFlow(UiStateListTasksList())
    val uiStateTasksListList: StateFlow<UiStateListTasksList> = _uiStateTasksListList

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

    fun fetchProjectsUser() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)

        val username = userPreferences.usernameFlow.first() ?: ""
        try {
            val request = UserRequest(username, "123")
            Log.d("RESPONSE","ovde")
            val response = repository.selectProjectUser(request)

            Log.d("RESPONSE",response.toString())

            _uiStateProjectsList.value = UiStateListProjects(
                listProjects = response,
                isRefreshing = false
            )

        } catch (e: Exception) {
            _uiStateProjectsList.value = UiStateListProjects(
                listProjects = null,
                isRefreshing = false
            )
        }
    }

     fun createProject(name:String)= viewModelScope.launch {
        val username = userPreferences.usernameFlow.first() ?: ""

        try {
            val request = NewProject(name,username)
            Log.d("RESPONSE","ovde")
            val response = repository.createProject(request)

            Log.d("RESPONSE",response.toString())
            fetchProjectsUser()


        } catch (e: Exception) {

        }
    }

    fun fetchAllTasksListProject(name:String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)

        val username = userPreferences.usernameFlow.first() ?: ""
        try {
            val request = NewProject(name,username)
            Log.d("RESPONSE","ovde")
            val response = repository.selectAllTasksProject(request)

            Log.d("RESPONSE",response.toString())


            _uiStateTasksListList.value = UiStateListTasksList(
                listTasks = response,
                isRefreshing = false
            )
            /*
            response.taskst?.forEach { taskList ->

                launch {
                    val tasks = repository.selectAllTasksOfTaskLists(TaskName(taskList.id))

                    // 3️⃣ Update state (VAŽNO: immutable copy)
                    response.taskst.update { currentMap ->
                        currentMap + (taskList.id to tasks)
                    }
                }
            }*/




        } catch (e: Exception) {
            _uiStateTasksListList.value = UiStateListTasksList(
                listTasks = null,
                isRefreshing = false
            )
        }
    }

    fun fetchAllTasksTasksList(name:Int) = viewModelScope.launch {

    }

}

data class UiStateUser(
    val user: MessageResponse? = null,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)

data class UiStateListProjects(
    val listProjects: ProjectList? =null,
    val isRefreshing: Boolean = false,
    val error: String? = null,
)

data class UiStateListTasksList(
    val listTasks: TasksListList? =null,
    val isRefreshing: Boolean = false,
    val error: String? = null,
)

data class UiStateNewProject(
    val newProject: NewProject? =null,
    val isRefreshing: Boolean = false,
    val error: String? = null,
)