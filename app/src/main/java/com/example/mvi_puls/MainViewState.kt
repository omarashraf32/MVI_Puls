package com.example.mvi_puls

sealed class MainViewState { //hna el (states) el htzhar le (User) lma y7sal (Action) mo3yan
    //idle
    object Idle: MainViewState() //leh 3mltha (object) 3shan mosh hya5od 7ga fe (parameter)
    //number
    data class Number(val number: Int): MainViewState() //leh 3mlnah hna (data class) 3shan hya5od (parameter) el hwa (Number)
    //Error
    data class Error(val error: String): MainViewState() //leh 3mlnah hna (data class) 3shan hya5od (parameter) el hwa (Error)
}