package com.example.primachiamatadirete

import retrofit2.Response
import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): Response<Data>
}