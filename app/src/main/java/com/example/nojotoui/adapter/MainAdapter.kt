package com.example.nojotoui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nojotoui.databinding.HomeItemBinding
import com.example.nojotoui.model.HomeModel

class MainAdapter(val list: List<HomeModel>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {


    class ViewHolder(val binding: HomeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(HomeItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.binding.ivImage).load(list[position].imgUrl).into(holder.binding.ivImage)
        Glide.with(holder.itemView).load(list[position].profileUrl).into(holder.binding.profileImage)
    }

    override fun getItemCount() = list.size
}