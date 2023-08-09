package com.example.product.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.product.R
import com.example.product.databinding.DialogKeyboardBinding
import com.example.product.databinding.ItemOrderBinding
import com.example.product.dialog.KeyboardDialog
import com.example.product.listense.IClickItemOrder
import com.example.product.model.Variants
import com.example.product.utils.GlobalFuntion

class OrderAdapter(var mListVariant: MutableList<Variants>,var onClickItemOrder:IClickItemOrder,var mContext: Context) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){
    class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        var binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.binding.tvNameVariant.text = mListVariant[position].name
        holder.binding.tvSku.text = "SKU:" + mListVariant[position].sku
        var count = 0f
        for (j in 0 until mListVariant[position].variant_prices.size) {
            if (mListVariant[position].variant_prices[j].price_list.code.equals("BANLE")) {
                count = mListVariant[position].variant_prices[j].value
                break
            }
        }
        holder.binding.tvQuantity.text =mListVariant[position].total.toString()
        holder.binding.tvQuantity.setOnClickListener{
            dialogKeyboard()
        }
        holder.binding.tvTotalRetailPrice.text = GlobalFuntion.removeDecimal((holder.binding.tvQuantity.text.toString().toInt()*count))
        holder.binding.imgAdd.setOnClickListener{
            holder.binding.tvQuantity.text=(holder.binding.tvQuantity.text.toString().toInt()+1).toString()
            onClickItemOrder.onClickItemOrder(mListVariant[position])
            holder.binding.tvTotalRetailPrice.text = GlobalFuntion.removeDecimal((holder.binding.tvQuantity.text.toString().toInt()*count))
        }
        holder.binding.imgLess.setOnClickListener{
            holder.binding.tvQuantity.text=((holder.binding.tvQuantity.text.toString().toInt()-1)).toString()
            holder.binding.tvTotalRetailPrice.text = GlobalFuntion.removeDecimal((holder.binding.tvQuantity.text.toString().toInt()*count))
        }
        holder.binding.imgCancel.setOnClickListener{
            mListVariant.removeAt(position)
            notifyDataSetChanged()
        }
        var count1 : Float = 0f
        for(i in 0 until mListVariant[position].inventories.size){
            count1+=mListVariant[position].inventories[i].available
        }
        if(count1<1){
            holder.binding.imgWarning.visibility=View.VISIBLE
            holder.binding.tvLackOfGoods.visibility=View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return mListVariant.size
    }
    fun dialogKeyboard() {
        var bindingDialog:DialogKeyboardBinding=DialogKeyboardBinding.inflate(LayoutInflater.from(mContext))
        val dialog:Dialog=KeyboardDialog.onCreateDialog(mContext,bindingDialog)
        bindingDialog.btnCancel.setOnClickListener{
            dialog.dismiss()
        }
        bindingDialog.btnConfirm.setOnClickListener{

        }

        dialog.show()


    }
}