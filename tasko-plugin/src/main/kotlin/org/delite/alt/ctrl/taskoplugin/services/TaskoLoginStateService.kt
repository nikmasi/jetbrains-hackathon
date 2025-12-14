package org.delite.alt.ctrl.taskoplugin.services

import com.intellij.openapi.components.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.util.userData

data class LoginState(var username: String = "", var password: String = "", var taskListIdx: Int = -1) {
    fun loggedIn(): Boolean {
        return username.length + password.length > 0
    }
}


@Service
@State(
    name = "LoginState",
    storages = [Storage("loginState.xml")]
)
class TaskoLoginStateService: PersistentStateComponent<LoginState> {
    private var state = LoginState()

    override fun getState(): LoginState {
        return state
    }

    override fun loadState(state: LoginState) {
        this.state = state
    }

    companion object {
        fun getInstance(): TaskoLoginStateService {
            return ApplicationManager
                .getApplication()
                .getService(TaskoLoginStateService::class.java)
        }
    }
}