package com.example.product.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.product.databinding.DialogKeyboardBinding
import com.example.product.ui.model.Variant
import com.example.product.utils.NumberUtil.formatNoTrailingZero


object KeyboardDialog :View.OnClickListener {
    private lateinit var mBinding: DialogKeyboardBinding
    private  lateinit var mVariant: Variant
     fun onCreateDialog(context: Context,binding: DialogKeyboardBinding,variant: Variant): Dialog {
        val builder = Dialog(context)
         mBinding =binding
         mVariant =variant
         initListener()
         builder.setContentView(mBinding.root)
        return builder
    }
    fun initListener(){
        mBinding.btnDialogBoard0.setOnClickListener(this)
        mBinding.btnDialogBoard1.setOnClickListener(this)
        mBinding.btnDialogBoard2.setOnClickListener(this)
        mBinding.btnDialogBoard3.setOnClickListener(this)
        mBinding.btnDialogBoard4.setOnClickListener(this)
        mBinding.btnDialogBoard5.setOnClickListener(this)
        mBinding.btnDialogBoard6.setOnClickListener(this)
        mBinding.btnDialogBoard7.setOnClickListener(this)
        mBinding.btnDialogBoard8.setOnClickListener(this)
        mBinding.btnDialogBoard9.setOnClickListener(this)
        mBinding.ivDialogKeyBoardClear.setOnClickListener(this)
        mBinding.ibtnDialogBoardBackpace.setOnClickListener(this)
        mBinding.btnDialogBoardDot.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            mBinding.btnDialogBoard0.id->{
                deleteNumberFirst()
                //mBinding.tvDialogKeybordTotal.text =mBinding.tvDialogKeybordTotal.text.toString() + mBinding.btnDialogBoard0.text
                mBinding.tvDialogKeybordTotal.text= (mVariant.total!!+ mBinding.btnDialogBoard0.text.toString().toDouble()).formatNoTrailingZero()
                mVariant.total= mBinding.tvDialogKeybordTotal.text.toString().toDouble()
            }
            mBinding.btnDialogBoard1.id->{
                deleteNumberFirst()
                mBinding.tvDialogKeybordTotal.text =
                    mBinding.tvDialogKeybordTotal.text.toString() + mBinding.btnDialogBoard1.text
            }
            mBinding.btnDialogBoard2.id->{
                deleteNumberFirst()
                mBinding.tvDialogKeybordTotal.text =
                    mBinding.tvDialogKeybordTotal.text.toString() + mBinding.btnDialogBoard2.text
            }
            mBinding.btnDialogBoard3.id->{
                deleteNumberFirst()
                mBinding.tvDialogKeybordTotal.text = (mBinding.tvDialogKeybordTotal.text.toString() + mBinding.btnDialogBoard3.text).toDouble().formatNoTrailingZero()
            }
            mBinding.btnDialogBoard4.id->{
                deleteNumberFirst()
                mBinding.tvDialogKeybordTotal.text =
                    mBinding.tvDialogKeybordTotal.text.toString() + mBinding.btnDialogBoard4.text
            }
            mBinding.btnDialogBoard5.id->{
                deleteNumberFirst()
                mBinding.tvDialogKeybordTotal.text = mBinding.tvDialogKeybordTotal.text.toString() + mBinding.btnDialogBoard5.text
            }
            mBinding.btnDialogBoard6.id->{
                deleteNumberFirst()
                mBinding.tvDialogKeybordTotal.text = mBinding.tvDialogKeybordTotal.text.toString() + mBinding.btnDialogBoard6.text
            }
            mBinding.btnDialogBoard7.id->{
                deleteNumberFirst()
                mBinding.tvDialogKeybordTotal.text =
                    mBinding.tvDialogKeybordTotal.text.toString() + mBinding.btnDialogBoard7.text
            }
            mBinding.btnDialogBoard8.id->{
                deleteNumberFirst()
                mBinding.tvDialogKeybordTotal.text = mBinding.tvDialogKeybordTotal.text.toString() + mBinding.btnDialogBoard8.text
            }
            mBinding.btnDialogBoard9.id->{
                deleteNumberFirst()
                mBinding.tvDialogKeybordTotal.text = limitStringTo8Characters(mBinding.tvDialogKeybordTotal.text.toString() + mBinding.btnDialogBoard9.text)
            }
            mBinding.ibtnDialogBoardBackpace.id->{
                if(mBinding.tvDialogKeybordTotal.text.isNotEmpty()&& mBinding.tvDialogKeybordTotal.text.length>1){
                    mBinding.tvDialogKeybordTotal.text = mBinding.tvDialogKeybordTotal.text.dropLast(1)
                }else if(mBinding.tvDialogKeybordTotal.text.length==1){
                    mBinding.tvDialogKeybordTotal.text="0"
                }
            }
            mBinding.ivDialogKeyBoardClear.id->{
                mBinding.tvDialogKeybordTotal.text="0"
            }
           mBinding.btnDialogBoardDot.id->{
               if(!mBinding.tvDialogKeybordTotal.text.contains(".")){
                   mBinding.tvDialogKeybordTotal.text = mBinding.tvDialogKeybordTotal.text.toString() + mBinding.btnDialogBoardDot.text
               }
            }
        }
    }
    fun deleteNumberFirst(){
        if(mBinding.tvDialogKeybordTotal.text.length==1&& mBinding.tvDialogKeybordTotal.text=="0"){
            mBinding.tvDialogKeybordTotal.text=""
        }
    }
    fun limitStringTo8Characters(input: String): String {
        return if (input.length > 8) {
            input.substring(0, 8)
        } else {
            input
        }
    }
}