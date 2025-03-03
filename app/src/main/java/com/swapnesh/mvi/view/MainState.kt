package com.swapnesh.mvi.view

import com.swapnesh.mvi.model.Animal

sealed class MainState {

    object  Idel : MainState()
    object  Loading : MainState()
    data class Animals(val animales: List<Animal>) : MainState()
    data class Error(val error:String) : MainState()
}