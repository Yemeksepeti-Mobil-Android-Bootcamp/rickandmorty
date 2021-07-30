package com.example.rickandmorty.utils.retrofitHelper

import android.content.SharedPreferences
import android.util.Log
import com.example.rickandmorty.models.listing.RickAndMortyBaseResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitResponseHandler {
    fun onResponse(response: RickAndMortyBaseResponse)
    fun onError()
}

class RetrofitHelper {

    private var sharedPreferences:SharedPreferences?=null

    private val okhttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor {
            var token= sharedPreferences?.getString("token","")
            val request =it.request().newBuilder().addHeader("X-Token","$token").build()
            it.proceed(request)
        })
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        .build()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttp)
        .build()

    private var service: RickAndMortyApiService =
        retrofit.create(RickAndMortyApiService::class.java)

    fun listCharacter(page: Int = 1, callBack: RetrofitResponseHandler) {
        service.listCharacters(page).enqueue(object : Callback<RickAndMortyBaseResponse> {
            override fun onResponse(
                call: Call<RickAndMortyBaseResponse>,
                response: Response<RickAndMortyBaseResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callBack.onResponse(it)
                    }
                } else {
                    callBack.onError()
                    Log.v("RetrofitHelper", "any errors.")
                }
            }

            override fun onFailure(call: Call<RickAndMortyBaseResponse>, t: Throwable) {
                Log.v("RetrofitHelper", "onFailure -> service unavailable errors.")
                callBack.onError()
            }
        })
    }

    fun setShearedPreferences(sharedPreferences: SharedPreferences):RetrofitHelper{
        this.sharedPreferences=sharedPreferences
        return this
    }
}