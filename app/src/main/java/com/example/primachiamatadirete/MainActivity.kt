package com.example.primachiamatadirete

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.primachiamatadirete.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dogApiService: DogApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dogApiService = retrofit.create(DogApiService::class.java)


        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = dogApiService.getRandomDogImage()
                if (response.isSuccessful) {
                    val dogImage = response.body()
                    binding.dog.visibility = View.VISIBLE
                    Picasso.get().load(dogImage?.message).into(binding.dog)
                } else {
                    Log.e("MainActivity", "Response not successful")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error: ${e.message}")
            }
        }

        /*CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = dogApiService.getRandomDogImage()
                if (response.isSuccessful) {
                    val dogImage = response.body()
                    // Visualizza l'immagine casuale del cane nella textview "dog"
                    binding.dog.text = dogImage?.message
                } else {
                    Log.e("MainActivity", "Response not successful")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error: ${e.message}")
            }
        }*/
    }
}