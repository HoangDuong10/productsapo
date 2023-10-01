package com.example.product.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.product.databinding.ItemImageBinding
import com.example.product.ui.model.Image

class ImageAdapter (var listImage : MutableList<Image>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding : ItemImageBinding = ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(listImage[position])
    }

    override fun getItemCount(): Int {
        return listImage.size
    }
    class ImageViewHolder(var binding:ItemImageBinding) : RecyclerView.ViewHolder(binding.root){
        private var ivImageProduct = binding.ivImageProduct
        fun bind(image:Image){
            Glide.with(ivImageProduct.context).load(image.fullPath).into(ivImageProduct)
        }
    }

}