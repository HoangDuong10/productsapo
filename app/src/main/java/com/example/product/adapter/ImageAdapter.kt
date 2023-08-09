package com.example.product.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.product.databinding.ItemImageBinding
import com.example.product.model.Image

class ImageAdapter (var list : MutableList<Image>,var mContext: Context) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder(var binding:ItemImageBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding : ItemImageBinding = ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(mContext).load(list[position].fullPath).into(holder.binding.imgProduct)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}