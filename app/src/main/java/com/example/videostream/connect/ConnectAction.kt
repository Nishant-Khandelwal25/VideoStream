package com.example.videostream.connect

sealed interface ConnectAction {
    data class OnNameChange(val name: String) : ConnectAction
    data object OnConnectClick : ConnectAction

}