package com.swapnesh.mvi.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.swapnesh.mvi.api.AnimalApi
import com.swapnesh.mvi.api.AnimalRepo


/*
class ViewModelFactory(private val api: AnimalApi): ViewModelProvider.Factory {

    fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(AnimalRepo(api)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}*/


class ViewModelFactory(private val api: AnimalApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(AnimalRepo(api)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
