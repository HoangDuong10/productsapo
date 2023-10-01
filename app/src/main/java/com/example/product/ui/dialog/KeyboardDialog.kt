package com.example.product.ui.dialog

import android.app.Dialog
import android.content.Context
import com.example.product.R
import com.example.product.databinding.DialogKeyboardBinding
import com.example.product.ui.model.OrderLineItem
import com.example.product.utils.NumberUtil.formatNumber


class KeyboardDialog(private val context: Context,private val mBinding: DialogKeyboardBinding,private val mOrderLineItem: OrderLineItem)  {
    //private lateinit var mBinding: DialogKeyboardBinding
    //private  lateinit var mOrderLineItem: OrderLineItem
     fun onCreateDialog(): Dialog {
        val builder = Dialog(context)
         builder.setContentView(mBinding.root)
         //mBinding =binding
         //mOrderLineItem =orderLineItem
         onClick()
        return builder
    }
    private fun onClick(){
        mBinding.btnDialogBoard0.setOnClickListener{addNumber(0)}
        mBinding.btnDialogBoard1.setOnClickListener{addNumber(1)}
        mBinding.btnDialogBoard2.setOnClickListener{addNumber(2)}
        mBinding.btnDialogBoard3.setOnClickListener{addNumber(3)}
        mBinding.btnDialogBoard4.setOnClickListener{addNumber(4)}
        mBinding.btnDialogBoard5.setOnClickListener{addNumber(5)}
        mBinding.btnDialogBoard6.setOnClickListener{addNumber(6)}
        mBinding.btnDialogBoard7.setOnClickListener{addNumber(7)}
        mBinding.btnDialogBoard8.setOnClickListener{addNumber(8)}
        mBinding.btnDialogBoard9.setOnClickListener{addNumber(9)}
        mBinding.ivDialogKeyBoardClear.setOnClickListener{mBinding.tvDialogKeybordQuantity.text="0"}
        mBinding.ibtnDialogBoardBackpace.setOnClickListener{ removeOne()}
        mBinding.btnDialogBoardDot.setOnClickListener{setDot()}
    }

    private fun limitStringToCharacters(input: String): String {
        return if (input.length > 11) {
            input.substring(0, 11)
        } else {
            input
        }
    }

    private fun addNumber(number:Int) {
        val quantity = limitStringToCharacters(mBinding.tvDialogKeybordQuantity.text.toString() + number)
        if(quantity.contains(".")){
            val beforeQuantity = quantity.substringBeforeLast(".")
            var afterQuantity = quantity.substringAfterLast(".")
            afterQuantity = if(afterQuantity.length>3){
                afterQuantity.substring(0,3)
            }else{
                afterQuantity
            }
            mBinding.tvDialogKeybordQuantity.text= context.getString(R.string.quantity_decimal,beforeQuantity.replace(",","").toDouble().formatNumber(),afterQuantity)
        }else{
            mBinding.tvDialogKeybordQuantity.text =quantity.replace(",","").toDouble().formatNumber()
        }
        if(mBinding.tvDialogKeybordQuantity.text.toString().replace(",","").toDouble()>999999.999){
            mBinding.tvDialogKeybordQuantity.text=context.getString(R.string.max_quantity)
        }

    }
    private fun removeOne(){
        if(mBinding.tvDialogKeybordQuantity.text.isNotEmpty()&& mBinding.tvDialogKeybordQuantity.text.length>1){
            mBinding.tvDialogKeybordQuantity.text = mBinding.tvDialogKeybordQuantity.text.dropLast(1)
        }else if(mBinding.tvDialogKeybordQuantity.text.length==1){
            mBinding.tvDialogKeybordQuantity.text="0"
        }
    }

    private fun setDot(){
        if(!mBinding.tvDialogKeybordQuantity.text.contains(".")){
            mBinding.tvDialogKeybordQuantity.text = context.getString(R.string.quantity,mBinding.tvDialogKeybordQuantity.text,mBinding.btnDialogBoardDot.text)
        }
    }


}