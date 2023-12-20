package com.example.mvi_puls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class AddNumberViewModel : ViewModel() {
    val intentChannel =
        Channel<MainIntent>(Channel.UNLIMITED) //3mlna el Channel 3shan Send beha (intent)me (Activity) le (viewModel)

    private val _viewState =
        MutableStateFlow<MainViewState>(MainViewState.Idle) //hna 3mlna el (Flow) 3shan ya5od el
    // (data) men el ViewModel yrg3ha le (Activity)  hna 3mlnha (Flow) 3shan mykrrsh el (data) el gya
    val state: StateFlow<MainViewState> get() = _viewState

    private var number = 0

    init {
        processIntent() //leh m3mlhsh (public)
    }

    //process on (Intent) come from (Activity)
    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.Addnumber -> addNumber()
                }
            }

        }
    }

    //reduce
    private fun addNumber() {
        viewModelScope.launch {
            _viewState.value = try {
                MainViewState.Number(++ number)
            } catch (e: Exception) {
                MainViewState.Error(e.message!!)
            }
        }
    }
}