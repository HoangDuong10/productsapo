package com.example.product.ui.dialog

import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.product.databinding.LayoutBottomSheetOrderSourceBinding
import com.example.product.ui.adapter.OrderSourceAdapter
import com.example.product.ui.model.OrderSource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragmentOrderSource(private var listOrderSource:MutableList<OrderSource>, private val sharedPreferences: SharedPreferences) : BottomSheetDialogFragment() {
    companion object{
        const val ITEM_POSITION:String = "ITEM_POSITION"
    }
    var onClickItem: ((orderSource: OrderSource) -> Unit)? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState)
        val binding = LayoutBottomSheetOrderSourceBinding.inflate(LayoutInflater.from(context))
        bottomSheetDialog.setContentView(binding.root)
        val linearLayoutManager1 = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rclvBottomSheetOrderSource.addItemDecoration(dividerItemDecoration)
        val adapter = OrderSourceAdapter(listOrderSource)
        binding.rclvBottomSheetOrderSource.layoutManager=linearLayoutManager1
        binding.rclvBottomSheetOrderSource.adapter=adapter

        adapter.onClickItem={ orderSource ->
            adapter.selectedItemPosition = listOrderSource.indexOf(orderSource)
            //orderSource.id?.let { sharedPreferences.edit().putInt(ORDER_SOURCE_ID, it) }
            sharedPreferences.edit().putInt(ITEM_POSITION, listOrderSource.indexOf(orderSource)).apply()
            adapter.notifyDataSetChanged()
            onClickItem?.invoke(orderSource)
        }
        val selectedItemPosition = sharedPreferences.getInt(ITEM_POSITION, -1)
        if (selectedItemPosition != -1) {
            adapter.selectedItemPosition = selectedItemPosition
            adapter.notifyDataSetChanged()
        }
        return bottomSheetDialog
    }
}