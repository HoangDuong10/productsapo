package com.example.product.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.product.R
import com.example.product.databinding.ItemLoadingBinding

import com.example.product.databinding.ItemProductBinding
import com.example.product.databinding.ItemVariantBinding
import com.example.product.ui.model.Product
import com.example.product.ui.model.Variant
import com.example.product.utils.NumberUtil.formatNoTrailingZero

class ProductAdapter(var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_PRODUCT: Int = 1
        const val TYPE_VARIANT: Int = 2
        const val TYPE_LOADING: Int = 3
    }

    private var listProduct: MutableList<Product> = mutableListOf()
    private var listVariant: MutableList<Variant> = mutableListOf()
    var onClickItemProduct: ((idProduct: Int) -> Unit)? = null
    var onClickItemVariant: ((idProduct: Int,idVariant:Int) -> Unit)? = null
    var modeView = TYPE_PRODUCT
    private var isLoadingAdd: Boolean = false
    fun getCurrentType(): Int {
        return modeView
    }

    override fun getItemViewType(position: Int): Int {
        if ((position == listProduct.size - 1 && isLoadingAdd) || (position == listVariant.size - 1&&isLoadingAdd)) {
            return TYPE_LOADING
        }
        return modeView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        if (TYPE_LOADING == viewType) {
            val loadingBinding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadingViewHolder(loadingBinding)
        } else {
            if (modeView == TYPE_PRODUCT) {
                return ProductViewHolder(binding)
            } else {
                val loadingBinding = ItemVariantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return VariantViewHolder(loadingBinding)
            }
        }
         return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (modeView == TYPE_PRODUCT) {
            listProduct.size
        } else {
            listVariant.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_PRODUCT) {
            val productViewHolder: ProductViewHolder = holder as ProductViewHolder
            productViewHolder.productBinding.tvProductName.text = listProduct[position].name
            productViewHolder.productBinding.tvProductCountVariant.text = listProduct[position].variants.size.toString()
            if (listProduct[position].images.isNotEmpty()) {
                Glide.with(mContext).load(listProduct[position].images[0].fullPath).into(productViewHolder.productBinding.ivProduct)
            } else {
                productViewHolder.productBinding.ivProduct.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_empty))
            }
            productViewHolder.productBinding.tvProductCountOnHand.text=listProduct[position].getTotalOnhand().formatNoTrailingZero()
            productViewHolder.productBinding.rlProductItem.setOnClickListener{
                onClickItemProduct?.invoke(listProduct[position].id!!)
            }
        } else if(holder.itemViewType == TYPE_VARIANT) {
            val variantViewHolder: VariantViewHolder = holder as VariantViewHolder
            variantViewHolder.variantBinding.tvVariantName.text = listVariant[position].name
            if (!listVariant[position].images.isNullOrEmpty()) {
                Glide.with(mContext).load(listVariant[position].images?.get(0)?.fullPath).into(variantViewHolder.variantBinding.ivVariant)
            } else {
                variantViewHolder.variantBinding.ivVariant.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_empty))
            }
            variantViewHolder.variantBinding.tvVariantSKU.text = listVariant[position].sku

            variantViewHolder.variantBinding.tvVariantOnHand.text = listVariant[position].getTotalOnhand().formatNoTrailingZero()
            variantViewHolder.variantBinding.tvVariantRetailPrice.text=listVariant[position].getRetailPrice().formatNoTrailingZero()
            variantViewHolder.variantBinding.rlVariantItem.setOnClickListener{
                onClickItemVariant?.invoke(listVariant[position].productId!!,listVariant[position].id!!)
            }
        }
    }
    fun addListProduct(data: List<Any>) {
        if (modeView == TYPE_PRODUCT) {
            this.listProduct.addAll(data as List<Product>)
        } else {
            this.listVariant.addAll(data as List<Variant>)
        }
    }

    fun setData(type: Int, data: List<Any>) {
        if (type == TYPE_PRODUCT) {
            listProduct.clear()
            listProduct.addAll(data as List<Product>)
            modeView= TYPE_PRODUCT
        } else{
            listVariant.clear()
            listVariant.addAll(data as List<Variant>)
            modeView= TYPE_VARIANT
        }

    }

    fun addFooterLoading() {
        isLoadingAdd = true
        if (modeView == TYPE_PRODUCT) {
            listProduct.add(Product())
        } else {
            listVariant.add((Variant()))
        }

    }

    fun remoteFooterLoading() {
        isLoadingAdd = false
        if (modeView == TYPE_PRODUCT) {
            val position = listProduct.size - 1
            listProduct.removeAt(position)
            notifyItemRemoved(position)
        } else {
            val position = listVariant.size - 1
            listVariant.removeAt(position)
            notifyItemRemoved(position)
        }

    }
    fun clearListProduct() {
        listProduct.clear()
        listVariant.clear()

    }
    class ProductViewHolder(val productBinding: ItemProductBinding) : RecyclerView.ViewHolder(productBinding.root){}
    class LoadingViewHolder(loadingBinding: ItemLoadingBinding) : RecyclerView.ViewHolder(loadingBinding.root) {}
    class VariantViewHolder(var variantBinding: ItemVariantBinding) : RecyclerView.ViewHolder(variantBinding.root){}
}

