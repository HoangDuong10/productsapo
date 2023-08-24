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

class DetailProductAdapter(var listVariant: MutableList<Variant>, var mContext: Context) : RecyclerView.Adapter<DetailProductAdapter.VariantProductViewHolder>() {
    var onClickItemVariant: ((idProduct: Int,idVariant:Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantProductViewHolder {
        val binding = ItemUnitBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VariantProductViewHolder(binding)
    }
    override fun onBindViewHolder(holder: VariantProductViewHolder, position: Int) {
        if(!listVariant[position].images.isNullOrEmpty()){
            Glide.with(mContext).load(listVariant[position].images?.get(0)!!.fullPath).into(holder.binding.ivUnitProduct)
        }
        else{
            holder.binding.ivUnitProduct.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_empty))
        }
        holder.binding.tvUnitName.text = listVariant[position].name?.replace(listVariant[position].productName+" - ","")
        holder.binding.tvUnitSKU.text = listVariant[position].sku
        holder.binding.tvUnitRetailPrice.text=listVariant[position].getRetailPrice().formatNoTrailingZero()
        holder.binding.tvUnitOnHand.text = listVariant[position].getTotalOnhand().formatNoTrailingZero()
        holder.binding.tvUnitAvailable.text = listVariant[position].getTotalAvailable().formatNoTrailingZero()
        for(i in 0 until position){
            for(j in position until position+1){
                if(listVariant[j].packsize&& listVariant[i].id==listVariant[j].packsizeRootId){
                    holder.binding.ivUnitSubdirectory.visibility = View.VISIBLE
                    holder.binding.tvUnitName.text = listVariant[position].unit
                    break
                }
            }
        }
        holder.binding.rlUnitItem.setOnClickListener{
            onClickItemVariant?.invoke(listVariant[position].productId!!,listVariant[position].id!!)
        }
    }
    override fun getItemCount(): Int {
        return listVariant.size
    }
    class VariantProductViewHolder(val binding: ItemUnitBinding) : RecyclerView.ViewHolder(binding.root){}
}