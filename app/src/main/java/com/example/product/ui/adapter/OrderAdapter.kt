package com.example.product.ui.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.product.R
import com.example.product.databinding.DialogDeleteProductBinding
import com.example.product.databinding.DialogKeyboardBinding
import com.example.product.databinding.ItemOrderBinding
import com.example.product.ui.dialog.KeyboardDialog
import com.example.product.ui.model.OrderLineItem

import com.example.product.utils.NumberUtil.formatNumber

class OrderAdapter(var listOrderLineItem: MutableList<OrderLineItem>, var context: Context) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>(){
    var onClickItemOrder: (()  ->Unit)?=null
    private lateinit var bindingDialog:DialogKeyboardBinding
    private lateinit var dialog : Dialog
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderViewHolder(binding)
    }
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(listOrderLineItem[position])
//        holder.binding.tvOrderQuantity.setOnClickListener{
//            dialogKeyboard(listOrderLineItem[position])
//            bindingDialog.btnDialogBoardConfirm.setOnClickListener{
//                //holder.binding.tvOrderQuantity.text = bindingDialog.tvDialogKeybordTotal.text
//                listOrderLineItem[position].quantity=(bindingDialog.tvDialogKeybordQuantity.text.toString().replace(",","").toDouble())
//                holder.binding.tvOrderQuantity.text=listOrderLineItem[position].quantity.formatNumber()
//                onClickItemOrder?.invoke()
//                dialog.dismiss()
//            }
//        }
//        holder.binding.ivOrderAdd.setOnClickListener{
//            holder.binding.tvOrderQuantity.text=(listOrderLineItem[position].quantity+1).formatNumber()
//            listOrderLineItem[position].quantity=listOrderLineItem[position].quantity+1
//            onClickItemOrder?.invoke()
//        }
//        holder.binding.ivOrderLess.setOnClickListener{
//            holder.binding.tvOrderQuantity.text=(listOrderLineItem[position].quantity-1).formatNumber()
//            listOrderLineItem[position].quantity=listOrderLineItem[position].quantity-1
//            onClickItemOrder?.invoke()
//            if(listOrderLineItem[position].quantity<1){
//                showAlertDialog(listOrderLineItem[position].variant.name!!,position)
//                holder.binding.tvOrderQuantity.text=(listOrderLineItem[position].quantity+1).formatNumber()
//                listOrderLineItem[position].quantity=listOrderLineItem[position].quantity+1
//            }
//        }
//        holder.binding.ivOrderCancel.setOnClickListener{
////            listOrderLineItem.removeAt(position)
////            notifyDataSetChanged()
////            onClickItemOrder?.invoke()
//            showAlertDialog(listOrderLineItem[position].variant.name!!,position)
//        }



    }

    override fun getItemCount(): Int {
        return listOrderLineItem.size
    }

    private fun dialogKeyboard(orderLineItem: OrderLineItem) {
        bindingDialog=DialogKeyboardBinding.inflate(LayoutInflater.from(context))
        val keyboardDialog = KeyboardDialog(context, bindingDialog, orderLineItem)
        dialog= keyboardDialog.onCreateDialog()
        bindingDialog.tvDialogKeybordQuantity.text=orderLineItem.quantity.formatNumber()
        dialog.show()
        bindingDialog.btnDialogBoardCancel.setOnClickListener{
            dialog.dismiss()
        }
    }
    private fun showAlertDialog(orderLineItem: OrderLineItem) {
        val builder = AlertDialog.Builder(context)
        val bindingDialog = DialogDeleteProductBinding.inflate(LayoutInflater.from(context))
        builder.setView(bindingDialog.root)
        val dialog = builder.create()
        bindingDialog.tvDialogDeleteProductContent.text=context.getString(R.string.ban_co_chac_chan_muon_xoa_san_pham_nay_ra_khoi_don_hang,orderLineItem.variant.name)
        dialog.show()

        bindingDialog.btnDialogDeleteProductCancel.setOnClickListener{
            dialog.dismiss()
        }
        bindingDialog.btnDialogDeleteProductConfirm.setOnClickListener{
            listOrderLineItem.remove(orderLineItem)
            onClickItemOrder?.invoke()
            //notifyItemRemoved(position)
            notifyDataSetChanged()
            dialog.dismiss()
        }
    }
    inner class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(orderLineItem: OrderLineItem){
            binding.tvOrderName.text = orderLineItem.variant.name
            binding.tvOrderSKU.text = orderLineItem.variant.sku
            binding.tvOrderRetailPrice.text = orderLineItem.variant.getRetailPrice().formatNumber()
            binding.tvOrderQuantity.text =orderLineItem.quantity.formatNumber()
            binding.tvOrderQuantity.setOnClickListener{
                dialogKeyboard(orderLineItem)
                bindingDialog.btnDialogBoardConfirm.setOnClickListener{
                    //holder.binding.tvOrderQuantity.text = bindingDialog.tvDialogKeybordTotal.text
                    orderLineItem.quantity=(bindingDialog.tvDialogKeybordQuantity.text.toString().replace(",","").toDouble())
                    binding.tvOrderQuantity.text=orderLineItem.quantity.formatNumber()
                    onClickItemOrder?.invoke()
                    dialog.dismiss()
                }
            }
            binding.ivOrderAdd.setOnClickListener{
                binding.tvOrderQuantity.text=(orderLineItem.quantity+1).formatNumber()
                orderLineItem.quantity=orderLineItem.quantity+1
                onClickItemOrder?.invoke()
            }
            binding.ivOrderLess.setOnClickListener{
                if(orderLineItem.quantity == 1.0){
                    showAlertDialog(orderLineItem)
                }else{
                    binding.tvOrderQuantity.text=(orderLineItem.quantity-1).formatNumber()
                    orderLineItem.quantity=orderLineItem.quantity-1
                }
                onClickItemOrder?.invoke()

            }

            binding.ivOrderCancel.setOnClickListener{
                showAlertDialog(orderLineItem)
            }
            if(orderLineItem.variant.getTotalAvailable()<1){
                binding.ivOrderWarning.visibility=View.VISIBLE
                binding.tvOrderLackOfGoods.visibility=View.VISIBLE
            }
        }
    }
}