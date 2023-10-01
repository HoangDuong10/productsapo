package com.example.product.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.product.R
import com.example.product.databinding.ItemLoadingBinding

import com.example.product.databinding.ItemProductBinding
import com.example.product.databinding.ItemVariantBinding
import com.example.product.ui.model.Product
import com.example.product.ui.model.Variant
import com.example.product.utils.NumberUtil.formatNumber

class ProductAdapter : RecyclerView.Adapter<ViewHolder>() {
    companion object {
        const val TYPE_PRODUCT: Int = 1
        const val TYPE_VARIANT: Int = 2
        const val TYPE_LOADING: Int = 3
    }
    var onClickItemProduct: ((idProduct: Int) -> Unit)? = null
    var onClickItemVariant: ((idProduct: Int,idVariant:Int) -> Unit)? = null
    var modeView = TYPE_PRODUCT
    private var isLoadingAdd: Boolean = false
    private var listData:MutableList<Any> = mutableListOf()
    override fun getItemViewType(position: Int): Int {
        if ((position == listData.size - 1 && isLoadingAdd)) {
            return TYPE_LOADING
        }
        return modeView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (TYPE_LOADING == viewType) {
            val loadingBinding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(loadingBinding)
        } else {
            if (viewType == TYPE_PRODUCT) {
                val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProductViewHolder(binding)
            } else {
                val loadingBinding = ItemVariantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VariantViewHolder(loadingBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ProductViewHolder) {
            holder.bind(listData[position] as Product)
            holder.itemView.setOnClickListener{
                val product=listData[position] as Product
                onClickItemProduct?.invoke(product.id!!)
            }
        } else if(holder is VariantViewHolder) {
            holder.bind(listData[position] as Variant)
            holder.itemView.setOnClickListener{
                val variant = listData[position] as Variant
                onClickItemVariant?.invoke(variant.productId!!,variant.id!!)
            }
        }
    }
    fun addListProduct(data: List<Any>) {
        val positionStart=listData.size
        listData.addAll(data)
        notifyItemRangeInserted(positionStart, data.size)

    }

    fun addFooterLoading() {
        isLoadingAdd = true
        listData.add(Variant())
        notifyItemInserted(listData.lastIndex)
    }

    fun remoteFooterLoading() {
        isLoadingAdd = false
        val position =listData.size-1
        listData.removeAt(position)
        notifyItemRemoved(position)

    }
    fun clearListProduct() {
        val itemCount = listData.size
        listData.clear()
        notifyItemRangeRemoved(0, itemCount)
    }
    class ProductViewHolder( productBinding: ItemProductBinding) : ViewHolder(productBinding.root){
        private var ivProduct: ImageView = productBinding.ivProduct
        private var tvName : TextView=productBinding.tvProductName
        private var tvCountVariant:TextView=productBinding.tvProductCountVariant
        private var tvOnHand:TextView=productBinding.tvProductCountOnHand
        fun bind(product: Product){
            tvName.text = product.name
            tvCountVariant.text = product.variants.size.toString()
            Glide.with(ivProduct.context).load(product.getFullPath()).placeholder(R.drawable.ic_empty).error(R.drawable.ic_empty).into(ivProduct)
            tvOnHand.text=product.getTotalOnhand().formatNumber()

        }
    }

    class VariantViewHolder( variantBinding: ItemVariantBinding) : ViewHolder(variantBinding.root){
        private var ivVariant: ImageView = variantBinding.ivVariant
        private var tvVariantName : TextView=variantBinding.tvVariantName
        private var tvSKU:TextView=variantBinding.tvSKU
        private var tvVariantOnHand:TextView=variantBinding.tvVariantOnHand
        private var tvVariantRetailPrice:TextView=variantBinding.tvVariantRetailPrice

        fun bind(variant:Variant){
            tvVariantName.text = variant.name
            Glide.with(ivVariant.context).load(variant.getFullPath()).placeholder(R.drawable.ic_empty).error(R.drawable.ic_empty).into(ivVariant)
            tvSKU.text = variant.sku
            tvVariantOnHand.text = variant.getTotalOnhand().formatNumber()
            tvVariantRetailPrice.text=variant.getRetailPrice().formatNumber()
        }
    }
    class LoadingViewHolder(loadingBinding: ItemLoadingBinding) : ViewHolder(loadingBinding.root) {}
}

