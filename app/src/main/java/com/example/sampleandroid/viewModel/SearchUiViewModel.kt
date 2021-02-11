package com.example.sampleandroid.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleandroid.util.State

class SearchUiViewModel : ViewModel() {
    private val _state = MutableLiveData<State>()

    val state: LiveData<State>
        get() = _state

    init {
        updateState(State.Idle)
    }

    fun updateState(state: State) {
        _state.postValue(state)
    }
}
