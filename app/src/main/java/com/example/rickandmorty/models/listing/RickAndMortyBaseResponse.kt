package com.example.rickandmorty.models.listing


import com.example.rickandmorty.models.core.Character
import com.google.gson.annotations.SerializedName

data class RickAndMortyBaseResponse(
    @SerializedName("info")
    val info: İnfo,
    @SerializedName("results")
    val characters: List<Character>
)