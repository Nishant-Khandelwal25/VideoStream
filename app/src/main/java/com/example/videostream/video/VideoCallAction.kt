package com.example.videostream.video

sealed interface VideoCallAction {
    data object OnDisconnectClick : VideoCallAction
    data object JoinCall : VideoCallAction
}