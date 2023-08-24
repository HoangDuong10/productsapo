package com.example.product.ui.createorder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.product.R
import com.example.product.converter.OrderConverter

import com.example.product.converter.VariantConverter
import com.example.product.ui.adapter.OrderAdapter
import com.example.product.databinding.ActivityCreateOrderBinding
import com.example.product.api.dto.OrderSource
import com.example.product.ui.model.Order
import com.example.product.ui.model.OrderLineItem
import com.example.product.ui.model.Variant
import com.example.product.presenter.CreateOrderPresenter
import com.example.product.api.response.OrderResponse
import com.example.product.ui.selectvariant.SelectVariantActivity
import com.example.product.utils.NumberUtil.formatNoTrailingZero

class CreateOrderActivity : AppCompatActivity(),CreateOrderContracts {
    private var listOrderSource: MutableList<OrderSource>?=null
    private var listVariant: MutableList<Variant>? = null
    private lateinit var binding: ActivityCreateOrderBinding
    private lateinit var createOrderPresenter: CreateOrderPresenter
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                listVariant = intent?.getSerializableExtra(SelectVariantActivity.KEY_LISTVARIANT) as? ArrayList<Variant>
                disPlayListOrder()
                displayButton()
                showInfoOrder()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createOrderPresenter= CreateOrderPresenter(this)
        goToSelectVariant()
        backPressed()
        displayButton()
        postData()
    }

    override fun callApiError() {
        Toast.makeText(this,getString(R.string.call_api_khong_thanh_cong),Toast.LENGTH_SHORT).show()
    }

    override fun callAPiSuccuss() {
        Toast.makeText(this,getString(R.string.call_api_thanh_cong),Toast.LENGTH_SHORT).show()
    }

    override fun callListSourceId(orderSource: MutableList<OrderSource>) {
        listOrderSource=orderSource
    }

    fun goToSelectVariant() {
        binding.llCreateOrderAddOrder.setOnClickListener {
            val intent = Intent(this, SelectVariantActivity::class.java)
            startForResult.launch(intent)
        }
        binding.tvCreateOrderSearch.setOnClickListener{
            val intent = Intent(this, SelectVariantActivity::class.java)
            startForResult.launch(intent)
        }
    }

    private fun disPlayListOrder() {
        binding.llCreateOrderAddOrder.visibility = View.GONE
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rclvCreateOrder.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rclvCreateOrder.addItemDecoration(dividerItemDecoration)
        val adapter = listVariant?.let {
            OrderAdapter(it, this)
        }
        binding.rclvCreateOrder.adapter = adapter
        adapter?.onClickItemOrder={
            showInfoOrder()
            displayButton()
        }

    }

    private fun showInfoOrder() {
        val countTax = listVariant?.sumOf { it.getTotalTaxOneOrder() }
        val countTotal = listVariant?.sumOf { it.total!!}
        val totalAmount = listVariant?.sumOf { it.getTotalAmountOneOrder() }
        val totalMoney=listVariant?.sumOf { it.getTotalMoney() }
        if(listVariant!!.all{ !it.taxable }){
            binding.rlCreateOrderTax.visibility=View.GONE
        }
        binding.tvCreateOrderTax.text = countTax?.formatNoTrailingZero()
        binding.tvCreateOrderTotal.text = countTotal?.formatNoTrailingZero()
        binding.tvCreateOrderTotalAmount.text = totalAmount?.formatNoTrailingZero()
        binding.tvCreateOrderTotalMoney.text=totalMoney?.formatNoTrailingZero()
    }
    private fun backPressed() {
        binding.ivCreateOrderLeft.setOnClickListener {
            finish()
        }
    }
    private fun postData(){
        createOrderPresenter.getSourceId()
        binding.btnCreateOrder.setOnClickListener{
            val orderLineItem =VariantConverter.toModelListOrderLineItem(listVariant as MutableList<Variant>)
            val order= Order(listOrderSource!![0].id,"draft", orderLineItem as MutableList<OrderLineItem>)
            val orderDTO= OrderConverter.toDTO(order)
            val orderResponse= OrderResponse(orderDTO)
            Log.d("aaa",""+orderResponse.order!!.orderLineItems.size)
            createOrderPresenter.postOrder(orderResponse)
        }

    }
    private fun displayButton(){
        if(listVariant.isNullOrEmpty()){
            binding.llCreateOrderAddOrder.visibility = View.VISIBLE
            binding.btnCreateOrder.alpha=0.5f
            binding.btnCreateOrder.isEnabled=false
        }else{
            binding.llCreateOrderAddOrder.visibility = View.GONE
            binding.btnCreateOrder.alpha=1f
            binding.btnCreateOrder.isEnabled=true
        }
    }


}
