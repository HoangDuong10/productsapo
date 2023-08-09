package com.example.product.adapter

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
import com.example.product.listense.IClickItemProduct
import com.example.product.model.Product
import com.example.product.model.Variants
import com.example.product.utils.GlobalFuntion

class ProductAdapter( var mList: MutableList<Product>,var mContext: Context,var iClickItemProduct : IClickItemProduct) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val TYPE_ITEM : Int = 1
    private val TYPE_LOADING : Int = 2
    private var isLoadingAdd : Boolean = false

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){

    }
    class LoadingViewHolder( val loadingBinding: ItemLoadingBinding) : RecyclerView.ViewHolder(loadingBinding.root){

    }
    fun setList(list: MutableList<Product>){
        this.mList.addAll(list);
    }

    override fun getItemViewType(position: Int): Int {
        if(position == mList.size-1&& isLoadingAdd){
            return TYPE_LOADING
        }
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(TYPE_ITEM==viewType){
            var binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ProductViewHolder(binding)
        }else{
            var loadingBinding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return LoadingViewHolder(loadingBinding)
        }


    }


    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType ==TYPE_ITEM){
            var productViewHolder : ProductViewHolder = holder as ProductViewHolder
            productViewHolder.binding.tvNameProduct.text = mList[position].name
            productViewHolder.binding.tvCountVariants.text = mList[position].variants.size.toString()
            if(!mList[position].images.isNullOrEmpty()){
                Glide.with(mContext).load(mList[position].images[0].fullPath).into(productViewHolder.binding.imgProduct)
            }else{
                productViewHolder.binding.imgProduct.setImageDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.ic_img_empty))
            }
            var count : Float = 0f
            for (i in 0 until mList[position].variants.size){
                var variants: Variants = mList[position].variants[i]
                for(j in 0 until variants.inventories.size){
                    count+=variants.inventories[j].available
                }
                if(i==mList[position].variants.size-1){
                    productViewHolder.binding.tvCountInventories.text = GlobalFuntion.removeDecimal(count)
                }
            }
            productViewHolder.binding.layoutItem.setOnClickListener{
                iClickItemProduct.onClickItemProduct(mList[position].id)
            }

        }
    }
    fun addFooterLoading(){
        isLoadingAdd = true
        mList.add(Product())


    }
    fun remoteFooterLoading(){
        isLoadingAdd = false
        var position = mList.size-1
        mList.removeAt(position)
        notifyItemRemoved(position)
    }
    fun clearListProduct(){
        mList.clear()
        notifyDataSetChanged()
    }
}