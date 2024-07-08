package com.example.videostream.connect

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.videostream.VideoStreamApp

class ConnectViewModel(
    private val app: Application
) : AndroidViewModel(app) {

    var state by mutableStateOf(ConnectState())
        private set

    fun onAction(action: ConnectAction) {
        when (action) {
            is ConnectAction.OnNameChange -> {
                state = state.copy(name = action.name)
            }

            is ConnectAction.OnConnectClick -> {
                connectToRoom()
            }
        }
    }

    private fun connectToRoom() {
        state = state.copy(errorMessage = null)
        if (state.name.isBlank()) {
            state = state.copy(
                errorMessage = "The name can't be empty."
            )
            return
        }
        (app as VideoStreamApp).init(state.name)
        state = state.copy(isConnected = true)
    }
}