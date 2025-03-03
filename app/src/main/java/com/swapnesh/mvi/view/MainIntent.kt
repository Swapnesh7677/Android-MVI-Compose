package com.swapnesh.mvi.view

sealed class MainIntent {
    object FetchAnimals: MainIntent()
}