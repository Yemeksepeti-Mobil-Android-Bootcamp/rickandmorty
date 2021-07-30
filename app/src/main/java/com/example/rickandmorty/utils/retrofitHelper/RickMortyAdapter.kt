package com.example.rickandmorty.utils.retrofitHelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.models.core.Character

class RickMortyAdapter:RecyclerView.Adapter<RickMortyAdapter.RickMortyViewHolder>() {

    var list= ArrayList<Character>()


    class RickMortyViewHolder(item: View) :RecyclerView.ViewHolder(item){

        private val rickAndMortyImageView:ImageView=item.findViewById(R.id.character_imageView)
        private val nameTaxtView:TextView=item.findViewById(R.id.characterName_textView)
        private val isAliveTextView:TextView=item.findViewById(R.id.isAlive_textView)
        fun bind(character: Character){
            Glide.with(rickAndMortyImageView.context)
                .load(character.image)
                .into(rickAndMortyImageView)
            nameTaxtView.text=character.name
            isAliveTextView.text=character.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RickMortyViewHolder {
        var viewItem=LayoutInflater.from(parent.context).inflate(R.layout.item_rickmorty,parent,false)
        return RickMortyViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: RickMortyViewHolder, position: Int) {
        var item=list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

    fun setRickAndMortyData(list: List<Character>){
        this.list=ArrayList(list)
        notifyDataSetChanged()

    }fun insertRickAndMortyData(list: List<Character>){
        var lastIndex=this.list.size
        this.list.addAll(ArrayList(list))
        notifyItemInserted(lastIndex)

    }



}