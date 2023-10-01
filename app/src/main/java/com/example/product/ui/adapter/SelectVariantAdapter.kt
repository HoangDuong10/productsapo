package com.example.product.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.product.R
import com.example.product.databinding.ItemLoadingBinding
import com.example.product.databinding.ItemVariantBinding
import com.example.product.ui.model.OrderLineItem
import com.example.product.utils.NumberUtil.formatNumber


class SelectVariantAdapter  : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
   companion object{
       const val TYPE_ITEM : Int = 1
       const val TYPE_LOADING : Int = 2
   }
    var mListOrderLineItem:  MutableList<OrderLineItem> = mutableListOf()
    private var listOrderLineItem:  MutableList<OrderLineItem> = mutableListOf()
    private var isLoadingAdd : Boolean = false
    var onClickItemVariant: ((orderLineItem: OrderLineItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(TYPE_ITEM == viewType){
            val itemBing = ItemVariantBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return VariantViewHolder(itemBing)
        }else{
            val loadingBinding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return LoadingViewHolder(loadingBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if(holder.itemViewType == TYPE_ITEM){
           val variantViewHolder : VariantViewHolder = holder as VariantViewHolder
           variantViewHolder.bind(listOrderLineItem[position])
           holder.itemBing.rlVariantItem.setOnClickListener{
               holder.itemBing.tvVariantTotal.visibility=View.VISIBLE
               listOrderLineItem[position].quantity=listOrderLineItem[position].quantity + 1
               holder.itemBing.tvVariantTotal.text = (listOrderLineItem[position].quantity).formatNumber()
               onClickItemVariant?.invoke(listOrderLineItem[position])

           }


       }
    }

    override fun getItemCount(): Int {
        return listOrderLineItem.size
    }

    override fun getItemViewType(position: Int): Int {
        if(position == listOrderLineItem.size-1 && isLoadingAdd){
            return TYPE_LOADING
        }
        return TYPE_ITEM
    }
    fun addFooterLoading(){
        isLoadingAdd = true
        listOrderLineItem.add(OrderLineItem())
    }
    fun removeFooterLoading(){
        isLoadingAdd = false
        val position: Int = listOrderLineItem.size-1
        listOrderLineItem.removeAt(position)
        notifyItemRemoved(position)
    }
    fun addList(list: MutableList<OrderLineItem>){
        val positionStart=listOrderLineItem.size
        listOrderLineItem.addAll(list)
        notifyItemRangeInserted(positionStart, list.size)

    }
    fun clearListVariant() {
        val itemCount = listOrderLineItem.size
        listOrderLineItem.clear()
        notifyItemRangeRemoved(0, itemCount)
    }
    class VariantViewHolder(val itemBing : ItemVariantBinding) : RecyclerView.ViewHolder(itemBing.root) {
        private var ivVariant=itemBing.ivVariant
        private var tvVariantName = itemBing.tvVariantName
        private var tvVariantSKU=itemBing.tvVariantSKU
        private var tvVariantOnHand=itemBing.tvVariantOnHand
        private var tvVariantRetailPrice=itemBing.tvVariantRetailPrice
        private var tvVariantTotal=itemBing.tvVariantTotal
        fun bind(orderLineItem: OrderLineItem){
            Glide.with(ivVariant.context).load(orderLineItem.variant.getFullPath()).error(R.drawable.ic_empty).placeholder(R.drawable.ic_empty).into(ivVariant)
            tvVariantName.text=orderLineItem.variant.name
            tvVariantSKU.text =orderLineItem.variant.sku
            tvVariantOnHand.text = orderLineItem.variant.getTotalOnhand().formatNumber()
            tvVariantRetailPrice.text=orderLineItem.variant.getRetailPrice().formatNumber()
            tvVariantTotal.text =  orderLineItem.quantity.formatNumber()
            if(tvVariantTotal.text.toString().replace(",","").toDouble()==0.0){
                tvVariantTotal.visibility=View.GONE
            }else{
                tvVariantTotal.visibility=View.VISIBLE
            }
        }

    }

    class LoadingViewHolder(loangBinding : ItemLoadingBinding) : RecyclerView.ViewHolder(loangBinding.root){}

}