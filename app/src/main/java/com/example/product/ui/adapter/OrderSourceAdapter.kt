package com.example.product.ui.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.product.databinding.ItemOrderSourceBinding
import com.example.product.ui.model.OrderSource

class OrderSourceAdapter(var listOrderSource: MutableList<OrderSource>) : RecyclerView.Adapter<OrderSourceAdapter.OrderSourceViewHolder>(){
    var selectedItemPosition: Int =0
    var onClickItem: ((orderSource: OrderSource) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSourceViewHolder {
        val binding = ItemOrderSourceBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderSourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderSourceViewHolder, position: Int) {
        if (position == selectedItemPosition) {
            holder.binding.ivOrderSourceCheck.visibility = View.VISIBLE
        } else {
            holder.binding.ivOrderSourceCheck.visibility = View.GONE
        }
        holder.bind(listOrderSource[position])
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(listOrderSource[position])
        }
    }

    override fun getItemCount(): Int {
        return listOrderSource.size
    }
    class OrderSourceViewHolder(val binding:ItemOrderSourceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderSource: OrderSource){
            binding.tvOrderSourceName.text= orderSource.name
        }
    }
}