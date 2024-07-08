package com.example.videostream.video

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.getstream.video.android.core.StreamVideo
import kotlinx.coroutines.launch

class VideoCallViewModel(
    private val streamVideo: StreamVideo
) : ViewModel() {
    var state by mutableStateOf(VideoCallState(call = streamVideo.call("default", "main-room")))
        private set

    fun onAction(action: VideoCallAction) {
        when (action) {
            is VideoCallAction.OnDisconnectClick -> {
                state.call.leave()
                streamVideo.logOut()
                state = state.copy(callState = CallState.ENDED)
            }

            is VideoCallAction.JoinCall -> {
                joinCall()
            }
        }
    }

    private fun joinCall() {
        if (state.callState == CallState.ACTIVE) {
            return
        }

        viewModelScope.launch {
            state = state.copy(callState = CallState.JOINING)
            val shouldConnect =
                streamVideo.queryCalls(filters = emptyMap()).getOrNull()?.calls?.isEmpty() == true

            state.call.join(shouldConnect).onSuccess {
                state = state.copy(
                    callState = CallState.ACTIVE,
                    errorMessage = null
                )
            }.onError {
                state = state.copy(callState = null, errorMessage = it.message)
            }
        }
    }
}