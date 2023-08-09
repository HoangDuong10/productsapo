package com.example.product.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast

object GlobalFuntion {

        fun startActivity(context: Context, clz: Class<*>) {
            var intent = Intent(context, clz)
            context.startActivity(intent)
        }
        fun showToastMessage(context: Context,message:String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    fun removeDecimal(value: Float): String {
        return if (value % 1 == 0f) {
            value.toInt().toString()
        } else {
            value.toString()
        }
    }
}