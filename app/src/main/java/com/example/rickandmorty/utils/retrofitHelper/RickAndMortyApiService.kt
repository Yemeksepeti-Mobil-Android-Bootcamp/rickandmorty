package com.example.rickandmorty.utils.retrofitHelper

import com.example.rickandmorty.models.listing.RickAndMortyBaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("character")
    fun listCharacters(@Query("page") page: Int): Call<RickAndMortyBaseResponse>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: Int): Call<Character>
}