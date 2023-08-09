package com.example.product.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.product.databinding.DialogKeyboardBinding


object KeyboardDialog :View.OnClickListener {
    private lateinit var mBinding: DialogKeyboardBinding
     fun onCreateDialog(context: Context,binding: DialogKeyboardBinding): Dialog {
        val builder = Dialog(context)
         mBinding=binding
         initListener()
         builder.setContentView(mBinding.root)
        return builder
    }
    fun initListener(){
        mBinding.tv0.setOnClickListener(this)
        mBinding.tv1.setOnClickListener(this)
        mBinding.tv2.setOnClickListener(this)
        mBinding.tv3.setOnClickListener(this)
        mBinding.tv4.setOnClickListener(this)
        mBinding.tv5.setOnClickListener(this)
        mBinding.tv6.setOnClickListener(this)
        mBinding.tv7.setOnClickListener(this)
        mBinding.tv8.setOnClickListener(this)
        mBinding.tv9.setOnClickListener(this)
        mBinding.imgClear.setOnClickListener(this)
        mBinding.imgBackspace.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            mBinding.tv0.id->{
                deleteNumberFirst()
                mBinding.tvTotal.text =mBinding.tvTotal.text.toString() + mBinding.tv0.text
            }
            mBinding.tv1.id->{
                deleteNumberFirst()
                mBinding.tvTotal.text =mBinding.tvTotal.text.toString() + mBinding.tv1.text
            }
            mBinding.tv2.id->{
                deleteNumberFirst()
                mBinding.tvTotal.text =mBinding.tvTotal.text.toString() + mBinding.tv2.text
            }
            mBinding.tv3.id->{
                deleteNumberFirst()
                mBinding.tvTotal.text = mBinding.tvTotal.text.toString() +mBinding.tv3.text
            }
            mBinding.tv4.id->{
                deleteNumberFirst()
                mBinding.tvTotal.text =mBinding.tvTotal.text.toString() + mBinding.tv4.text
            }
            mBinding.tv5.id->{
                deleteNumberFirst()
                mBinding.tvTotal.text = mBinding.tvTotal.text.toString() +mBinding.tv5.text
            }
            mBinding.tv6.id->{
                deleteNumberFirst()
                mBinding.tvTotal.text = mBinding.tvTotal.text.toString() +mBinding.tv6.text
            }
            mBinding.tv7.id->{
                deleteNumberFirst()
                mBinding.tvTotal.text =mBinding.tvTotal.text.toString() + mBinding.tv7.text
            }
            mBinding.tv8.id->{
                deleteNumberFirst()
                mBinding.tvTotal.text = mBinding.tvTotal.text.toString() +mBinding.tv8.text
            }
            mBinding.tv9.id->{
                deleteNumberFirst()
                mBinding.tvTotal.text = mBinding.tvTotal.text.toString() +mBinding.tv9.text
            }
            mBinding.imgBackspace.id->{
                if(mBinding.tvTotal.text.isNotEmpty()&&mBinding.tvTotal.text.length>1){
                    mBinding.tvTotal.text = mBinding.tvTotal.text.substring(0, mBinding.tvTotal.text.length - 1)
                }else if(mBinding.tvTotal.text.length==1){
                    mBinding.tvTotal.text="0"
                }
            }
            mBinding.imgClear.id->{
                mBinding.tvTotal.text="0"
            }


        }
    }
    fun deleteNumberFirst(){
        if(mBinding.tvTotal.text.length==1&& mBinding.tvTotal.text=="0"){
            mBinding.tvTotal.text=""
        }
    }
}