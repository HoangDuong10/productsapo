package com.example.product.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.product.R
import com.example.product.databinding.ItemLoadingBinding
import com.example.product.databinding.ItemVariantBinding
import com.example.product.ui.model.Variant
import com.example.product.utils.NumberUtil.formatNoTrailingZero


class SelectVariantAdapter (var mListVariant : MutableList<Variant>, var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
   companion object{
       const val TYPE_ITEM : Int = 1
       const val TYPE_LOADING : Int = 2
   }

    private var isLoadingAdd : Boolean = false
    var onClickItemVariant: ((idVariant: Int,variant: Variant) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(TYPE_ITEM == viewType){
            val itemBing = ItemVariantBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return VariantViewHolder(itemBing)
        }else{
            val loadingBinding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return loadingViewHolder(loadingBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if(holder.itemViewType == TYPE_ITEM){
           val variantViewHolder : VariantViewHolder = holder as VariantViewHolder
           variantViewHolder.itemBing.tvVariantName.text = mListVariant[position].name
           if(!mListVariant[position].images.isNullOrEmpty()){
               Glide.with(mContext).load(mListVariant[position].images?.get(0)?.fullPath).into(variantViewHolder.itemBing.ivVariant)
           }
           else{
               variantViewHolder.itemBing.ivVariant.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_empty))
           }
           variantViewHolder.itemBing.tvVariantSKU.text =mListVariant[position].sku
           variantViewHolder.itemBing.tvVariantOnHand.text = mListVariant[position].getTotalOnhand().formatNoTrailingZero()
           variantViewHolder.itemBing.tvVariantRetailPrice.text=mListVariant[position].getRetailPrice().formatNoTrailingZero()
           variantViewHolder.itemBing.tvVariantTotal.text = if (mListVariant[position].total !=null) mListVariant[position].total!!.formatNoTrailingZero() else "0"
           if(  variantViewHolder.itemBing.tvVariantTotal.text.toString().toDouble()==0.0){
               variantViewHolder.itemBing.tvVariantTotal.visibility=View.GONE
           }else{
               variantViewHolder.itemBing.tvVariantTotal.visibility=View.VISIBLE
           }
           variantViewHolder.itemBing.rlVariantItem.setOnClickListener{
               variantViewHolder.itemBing.tvVariantTotal.visibility=View.VISIBLE
               variantViewHolder.itemBing.tvVariantTotal.text =if (mListVariant[position].total !=null) (mListVariant[position].total!!+1).formatNoTrailingZero() else "1"
               onClickItemVariant?.invoke(mListVariant[position].id!!,mListVariant[position])
           }
       }
    }
    override fun getItemCount(): Int {
        return mListVariant.size
    }

    override fun getItemViewType(position: Int): Int {
        if(position == mListVariant.size-1 && isLoadingAdd){
            return TYPE_LOADING
        }
        return TYPE_ITEM
    }
    fun addFooterLoading(){
        isLoadingAdd = true
        mListVariant.add(Variant())
    }
    fun removeFooterLoading(){
        isLoadingAdd = false
        val position: Int = mListVariant.size-1
        mListVariant.removeAt(position)
        notifyItemRemoved(position)
    }
    fun addList(list: MutableList<Variant>){
        this.mListVariant.addAll(list)
    }
    class VariantViewHolder(val itemBing : ItemVariantBinding) : RecyclerView.ViewHolder(itemBing.root) {}
    class loadingViewHolder(loangBinding : ItemLoadingBinding) : RecyclerView.ViewHolder(loangBinding.root){}

}