package com.example.expensetrackerv2.ui.main.features.bottom_bar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainBottomBarViewModel @Inject constructor(
    private val channel: Channel<MainBottomBarEvent>
): ViewModel() {
    fun onEvent(event: MainBottomBarEvent) {
        viewModelScope.launch {
            channel.send(event)
        }
    }
}