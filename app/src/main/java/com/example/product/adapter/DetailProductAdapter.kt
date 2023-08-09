package com.example.product.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.product.R
import com.example.product.databinding.ItemDetailProductBinding


import com.example.product.model.Variants
import com.example.product.utils.GlobalFuntion

class DetailProductAdapter(var mList: MutableList<Variants>, var mContext: Context) : RecyclerView.Adapter<DetailProductAdapter.VariantProductViewHolder>() {
    class VariantProductViewHolder(val binding: ItemDetailProductBinding) : RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantProductViewHolder {
        var binding = ItemDetailProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VariantProductViewHolder(binding)
    }
    override fun onBindViewHolder(holder: VariantProductViewHolder, position: Int) {
        if(!mList[position].images.isNullOrEmpty()){

            Glide.with(mContext).load(mList[position].images[0].fullPath).into(holder.binding.imgVariant)
        }
        else{
            holder.binding.imgVariant.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_img_empty))
        }
        holder.binding.tvNameVariant.text = mList[position].name.replace(mList[position].product_name+" - ","")
        holder.binding.tvSku.text = "SKU : "+ mList[position].sku
        for (i in 0 until mList[position].variant_prices.size){
            if(mList[position].variant_prices[i].price_list.code.equals("BANLE")){
                holder.binding.tvRetailPrice.text =  GlobalFuntion.removeDecimal(mList[position].variant_prices[i].value)
                break
            }
        }
        var countInventory : Float = 0f
        var countCanSell :Float = 0f
        for(i in 0 until mList[position].inventories.size){
            countInventory+=mList[position].inventories[i].available
            countCanSell+=mList[position].inventories[i].on_hand
        }
        holder.binding.tvCountInventories.text = "Tồn kho: "+ GlobalFuntion.removeDecimal(countInventory)
        holder.binding.tvCanSell.text = "Có thể bán: "+GlobalFuntion.removeDecimal(countCanSell)
        for(i in 0 until position){
            for(j in position until position+1){
                if(mList[j].packsize&& mList[i].id==mList[j].packsize_root_id){
                    holder.binding.imgSubdirectory.visibility = View.VISIBLE
                    holder.binding.tvNameVariant.text = mList[position].unit
                    break
                }
            }
        }
//        if(!mList[position].packsize){
//            holder.binding.imgSubdirectory.visibility = View.GONE
//            holder.binding.tvNameVariant.text = mList[position].name.replace(mList[position].product_name+" - ","")
//        }
    }
    override fun getItemCount(): Int {
        return mList.size
    }
}