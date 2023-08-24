package com.example.product.ui.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.product.R
import com.example.product.databinding.DialogDeleteProductBinding
import com.example.product.databinding.DialogKeyboardBinding
import com.example.product.databinding.ItemOrderBinding
import com.example.product.ui.dialog.KeyboardDialog
import com.example.product.ui.model.Variant
import com.example.product.ui.AppConfig

import com.example.product.utils.NumberUtil.formatNoTrailingZero

class OrderAdapter(var listVariant: MutableList<Variant>, var mContext: Context) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){
    private lateinit var bindingDialog:DialogKeyboardBinding
    private lateinit var dialog : Dialog
    var onClickItemOrder: (()  ->Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderViewHolder(binding)
    }
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.binding.tvOrderName.text = listVariant[position].name
        holder.binding.tvOrderSKU.text = "SKU:" + listVariant[position].sku
        holder.binding.tvOrderRetailPrice.text = listVariant[position].getRetailPrice().formatNoTrailingZero()
        holder.binding.tvOrderQuantity.text =listVariant[position].total!!.formatNoTrailingZero()
        holder.binding.tvOrderQuantity.setOnClickListener{
            dialogKeyboard(listVariant[position])
            bindingDialog.btnDialogBoardConfirm.setOnClickListener{
                //holder.binding.tvOrderQuantity.text = bindingDialog.tvDialogKeybordTotal.text
                listVariant[position].total=(bindingDialog.tvDialogKeybordTotal.text.toString().toDouble())
                holder.binding.tvOrderQuantity.text=listVariant[position].total!!.formatNoTrailingZero()
                onClickItemOrder?.invoke()
                dialog.dismiss()
            }
        }
        holder.binding.ivOrderAdd.setOnClickListener{
            holder.binding.tvOrderQuantity.text=(listVariant[position].total!!+1).formatNoTrailingZero()
            listVariant[position].total=listVariant[position].total!!+1
            onClickItemOrder?.invoke()
        }
        holder.binding.ivOrderLess.setOnClickListener{
            holder.binding.tvOrderQuantity.text=(listVariant[position].total!!-1).formatNoTrailingZero()
            listVariant[position].total=listVariant[position].total!!-1
            onClickItemOrder?.invoke()
            if(listVariant[position].total!!<1){
                showAlertDialog(listVariant[position].name!!,position)
                holder.binding.tvOrderQuantity.text=(listVariant[position].total!!+1).formatNoTrailingZero()
                listVariant[position].total=listVariant[position].total!!+1
            }
        }
        holder.binding.ivOrderCancel.setOnClickListener{
            listVariant.removeAt(position)
            notifyDataSetChanged()
            onClickItemOrder?.invoke()
        }
        var countTotal  = 0.0
        for(i in 0 until listVariant[position].inventories.size){
            countTotal+= listVariant[position].inventories[i].available!!
        }
        if(countTotal<1){
            holder.binding.ivOrderWarning.visibility=View.VISIBLE
            holder.binding.tvOrderLackOfGoods.visibility=View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        return listVariant.size
    }

    private fun dialogKeyboard(variant: Variant) {
        bindingDialog=DialogKeyboardBinding.inflate(LayoutInflater.from(mContext))
        dialog= KeyboardDialog.onCreateDialog(mContext,bindingDialog,variant)
        bindingDialog.tvDialogKeybordTotal.text=variant.total!!.formatNoTrailingZero()
        dialog.show()
        bindingDialog.btnDialogBoardCancel.setOnClickListener{
            dialog.dismiss()
        }
    }
    private fun showAlertDialog(s:String,position:Int) {
        val builder = AlertDialog.Builder(mContext)
        val bindingDialog = DialogDeleteProductBinding.inflate(LayoutInflater.from(mContext))
        builder.setView(bindingDialog.root)
        bindingDialog.tvDialogDeleteProductContent.text=mContext.getString(R.string.ban_co_chac_chan_muon_xoa_san_pham)+AppConfig.Space+s+AppConfig.Space+mContext.getString(R.string.nay_ra_khoi_don_hang)
        val dialog = builder.create()
        dialog.show()
        bindingDialog.btnDialogDeleteProductCancel.setOnClickListener{
            dialog.dismiss()
        }
        bindingDialog.btnDialogDeleteProductConfirm.setOnClickListener{
            listVariant.removeAt(position)
            notifyDataSetChanged()
            dialog.dismiss()
        }
    }
    class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root){

    }
}