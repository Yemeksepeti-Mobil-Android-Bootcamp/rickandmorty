package com.example.rickandmorty.fragment.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.models.listing.RickAndMortyBaseResponse
import com.example.rickandmorty.utils.retrofitHelper.RetrofitHelper
import com.example.rickandmorty.utils.retrofitHelper.RetrofitResponseHandler
import com.example.rickandmorty.utils.retrofitHelper.RickMortyAdapter


class ListFragment : Fragment() {

    private var adapter= RickMortyAdapter()
    private var retrofitHelper:RetrofitHelper?=null
    private var page=1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrofitHelper= RetrofitHelper()
        val sharedPref=context?.getSharedPreferences("kodluyoruz",Context.MODE_PRIVATE)
        var editor= sharedPref!!.edit()
        editor.putString("token","hello kodluyoruz")
        editor.apply()
        retrofitHelper?.setShearedPreferences(sharedPref!!)
        initViews(view)
        fetchData(page)
    }

    private fun fetchData(page:Int=1) {

        Toast.makeText(context,"ListFragment.fetchData Page : $page",Toast.LENGTH_LONG).show()
        retrofitHelper?.listCharacter(page = page, object : RetrofitResponseHandler {
            override fun onError() {
                Log.v("MainActivity", "Error :(")

            }

            override fun onResponse(response: RickAndMortyBaseResponse) {
                adapter.setRickAndMortyData(response.characters)
            }
        })
    }
    private fun fetchDataWithScrool(page:Int=1) {

        Toast.makeText(context,"ListFragment.fetchData Page : $page",Toast.LENGTH_LONG).show()
        retrofitHelper?.listCharacter(page = page, object : RetrofitResponseHandler {
            override fun onError() {
                Log.v("MainActivity", "Error :(")

            }

            override fun onResponse(response: RickAndMortyBaseResponse) {
                adapter.insertRickAndMortyData(response.characters)
            }
        })
    }

    private fun initViews(view: View) {
        val recyclerView:RecyclerView=view.findViewById(R.id.rickAndMorty_recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(context, "Last", Toast.LENGTH_LONG).show()
                    page++
                    fetchDataWithScrool(page)
                }
            }
        })
        recyclerView.adapter=adapter
        val nextButton=view.findViewById<Button>(R.id.next_button)
        val backButton=view.findViewById<Button>(R.id.back_button)

        nextButton.setOnClickListener {
            page++
            fetchData(page)
        }
        backButton.setOnClickListener {
            if (page!=1){
                page--
                fetchData(page)
            }
        }
    }

}