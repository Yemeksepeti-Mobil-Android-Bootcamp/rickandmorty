package com.example.rickandmorty.utils.retrofitHelper

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtil {
    var sharedPreferences:SharedPreferences?=null

    fun intiSharedPreferences(context:Context){
        sharedPreferences=context.getSharedPreferences("sharedPreferences",Context.MODE_PRIVATE)
    }
    fun saveString(key:String,value:String){
        sharedPreferences?.let {
            val editor=it.edit()
            editor.putString(key,value)
            editor.apply()
        }
    }
    fun getString(key:String,defValue:String=""):String{
        return sharedPreferences?.getString(key,defValue)?:""
    }
}