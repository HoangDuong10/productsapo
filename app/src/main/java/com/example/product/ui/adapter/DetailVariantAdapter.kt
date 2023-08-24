package com.example.product.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.product.R
import com.example.product.databinding.ItemUnitBinding
import com.example.product.ui.model.Variant
import com.example.product.utils.NumberUtil.formatNoTrailingZero

class DetailVariantAdapter(var mList: MutableList<Variant>, var mContext: Context) : RecyclerView.Adapter<DetailVariantAdapter.VariantProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantProductViewHolder {
        var binding = ItemUnitBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VariantProductViewHolder(binding)
    }
    override fun onBindViewHolder(holder: VariantProductViewHolder, position: Int) {
        if(!mList[position].images.isNullOrEmpty()){

            Glide.with(mContext).load(mList[position].images?.get(0)!!.fullPath).into(holder.binding.ivUnitProduct)
        }
        else{
            holder.binding.ivUnitProduct.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_empty))
        }
        holder.binding.tvUnitName.text = mList[position].unit
        holder.binding.tvUnitSKU.text = mList[position].sku
        holder.binding.tvUnitRetailPrice.text=mList[position].getRetailPrice().formatNoTrailingZero()
        holder.binding.tvUnitOnHand.text = mList[position].getTotalOnhand().formatNoTrailingZero()
        holder.binding.tvUnitAvailable.text = mList[position].getTotalAvailable().formatNoTrailingZero()
        holder.binding.ivUnitSubdirectory.visibility =View.VISIBLE
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class VariantProductViewHolder(val binding: ItemUnitBinding) : RecyclerView.ViewHolder(binding.root){

    }
}