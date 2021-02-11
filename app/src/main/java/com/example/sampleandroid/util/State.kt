package com.example.sampleandroid.util

sealed class State {
    object Idle : State()
    object Loading : State()
    data class Error(val error: String) : State()
}
