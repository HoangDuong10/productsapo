package com.example.product.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.product.R
import com.example.product.databinding.ItemLoadingBinding
import com.example.product.databinding.ItemVariantBinding
import com.example.product.listense.IClickItemVariant
import com.example.product.model.Variants
import com.example.product.utils.GlobalFuntion


class VariantAdapter (var mListVariant : MutableList<Variants>,var mContext: Context,var iClickItemVariant: IClickItemVariant) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var TYPE_ITEM : Int = 1
    private var TYPE_LOADING : Int = 2
    private var isLoadingAdd : Boolean = false
    class VariantViewHolder(val itemBing : ItemVariantBinding) : RecyclerView.ViewHolder(itemBing.root) {

    }
    class loadingViewHolder(val loangBinding : ItemLoadingBinding) : RecyclerView.ViewHolder(loangBinding.root){


    }
    fun setList(list: MutableList<Variants>){
        this.mListVariant.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(TYPE_ITEM == viewType){
            var itemBing = ItemVariantBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return VariantViewHolder(itemBing)
        }else{
            var loadingBinding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return loadingViewHolder(loadingBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if(holder.itemViewType == TYPE_ITEM){
           var variantViewHolder : VariantViewHolder = holder as VariantViewHolder
           variantViewHolder.itemBing.tvNameVariant.text = mListVariant[position].name
           if(!mListVariant[position].images.isNullOrEmpty()){
               Glide.with(mContext).load(mListVariant[position].images?.get(0)?.fullPath).into(variantViewHolder.itemBing.imgVariant)
           }
           else{
               variantViewHolder.itemBing.imgVariant.setImageDrawable(ContextCompat.getDrawable(mContext,
                   R.drawable.ic_img_empty))
           }
           variantViewHolder.itemBing.tvSku.text ="SKU:" +mListVariant[position].sku
           var count : Float = 0f
           for(i in 0 until mListVariant[position].inventories.size){
               count+=mListVariant[position].inventories[i].available
           }
           variantViewHolder.itemBing.tvCountInventories.text = GlobalFuntion.removeDecimal(count)
           for(j in 0 until mListVariant[position].variant_prices.size){
                if(mListVariant[position].variant_prices[j].price_list.code.equals("BANLE")){
                    variantViewHolder.itemBing.tvRetailPrice.text = GlobalFuntion.removeDecimal(mListVariant[position].variant_prices[j].value)
                    break
                }
           }
           variantViewHolder.itemBing.layoutItem.setOnClickListener{
               iClickItemVariant.onClickItemProduct(mListVariant[position].product_id,mListVariant[position].id)
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
        mListVariant.add(Variants())
    }
    fun removeFooterLoading(){
        isLoadingAdd = false
        var position: Int = mListVariant.size-1
        mListVariant.removeAt(position)
        notifyItemRemoved(position)
    }
    fun clearListVariant(){
        mListVariant.clear()
        notifyDataSetChanged()
    }

}