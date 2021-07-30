package com.example.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.models.listing.RickAndMortyBaseResponse
import com.example.rickandmorty.utils.retrofitHelper.RetrofitHelper
import com.example.rickandmorty.utils.retrofitHelper.RetrofitResponseHandler
import com.example.rickandmorty.utils.retrofitHelper.RickMortyAdapter

class MainActivity : AppCompatActivity() {

    private val retrofitHelper = RetrofitHelper()
    private val adapter: RickMortyAdapter = RickMortyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchData()
        initViews()
    }

    private fun fetchData() {
        retrofitHelper.listCharacter(page = 1, object : RetrofitResponseHandler {
            override fun onError() {
                Log.v("MainActivity", "Error :(")

            }

            override fun onResponse(response: RickAndMortyBaseResponse) {
                adapter.setRickAndMortyData(response.characters)
            }
        })
    }

    private fun initViews() {
        val recyclerView: RecyclerView = findViewById(R.id.rickAndMorty_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
        recyclerView.adapter = adapter

    }
}