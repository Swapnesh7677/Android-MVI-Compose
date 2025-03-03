package com.swapnesh.mvi.api

import com.swapnesh.mvi.model.Animal
import retrofit2.http.GET

interface AnimalApi {
    @GET("animals.json")
    suspend fun getAnimals(): List<Animal>
}