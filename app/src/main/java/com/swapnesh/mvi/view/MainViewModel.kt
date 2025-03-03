package com.swapnesh.mvi.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swapnesh.mvi.api.AnimalRepo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class  MainViewModel(private val repo:AnimalRepo):ViewModel(){

    val userInent =  Channel<MainIntent>(Channel.UNLIMITED)

    var state = mutableStateOf<MainState>(MainState.Idel)


    init {
        handleIntent()
    }

    private  fun handleIntent(){
        viewModelScope.launch {
            userInent.consumeAsFlow().collect{ collector->
                when (collector){
                    MainIntent.FetchAnimals -> fetchAnimal()
                }

            }
        }
    }

    private fun fetchAnimal() {
        viewModelScope.launch {
            state.value =MainState.Loading

            state.value = try {
                    MainState.Animals(repo.getAnimals())
            }catch (e:Exception){
                    MainState.Error(e.localizedMessage)
            }

        }
    }
}