package com.example.product.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.product.R
import com.example.product.databinding.ItemUnitBinding
import com.example.product.ui.model.Variant
import com.example.product.utils.NumberUtil.formatNumber

class DetailVariantAdapter(var listVariant: MutableList<Variant>) : RecyclerView.Adapter<DetailVariantAdapter.VariantProductViewHolder>() {
    var onClickItemVariant: ((idProduct: Int,idVariant:Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantProductViewHolder {
        val binding = ItemUnitBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VariantProductViewHolder(binding)
    }
    override fun onBindViewHolder(holder: VariantProductViewHolder, position: Int) {
        holder.bind(listVariant[position])
        holder.itemView.setOnClickListener{
            onClickItemVariant?.invoke(listVariant[position].productId!!,listVariant[position].id!!)
        }
    }
    override fun getItemCount(): Int {
        return listVariant.size
    }
    class VariantProductViewHolder(val binding: ItemUnitBinding) : RecyclerView.ViewHolder(binding.root){
        private var ivUnitProduct = binding.ivUnitProduct
        private var tvUnitName = binding.tvUnitName
        private var tvUnitSKU = binding.tvUnitSKU
        private var tvUnitRetailPrice = binding.tvUnitRetailPrice
        private var tvUnitOnHand = binding.tvUnitOnHand
        private var tvUnitAvailable = binding.tvUnitAvailable
        private var ivUnitSubdirectory = binding.ivUnitSubdirectory
        fun bind(variant: Variant){
            Glide.with(ivUnitProduct.context).load(variant.getFullPath()).error(R.drawable.ic_empty).placeholder(R.drawable.ic_empty).into(ivUnitProduct)
            tvUnitName.text = variant.name
            tvUnitSKU.text = variant.sku
            tvUnitRetailPrice.text = variant.getRetailPrice().formatNumber()
            tvUnitOnHand.text = variant.getTotalOnhand().formatNumber()
            tvUnitAvailable.text = variant.getTotalAvailable().formatNumber()
            if(variant.packsize){
                ivUnitSubdirectory.visibility = View.VISIBLE
                tvUnitName.text = variant.unit
            }
        }
    }
}