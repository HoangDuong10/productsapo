package com.example.product.ui.createorder

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.product.R
import com.example.product.ui.adapter.OrderAdapter
import com.example.product.databinding.ActivityCreateOrderBinding
import com.example.product.ui.model.OrderLineItem
import com.example.product.presenter.CreateOrderPresenter
import com.example.product.ui.model.Order
import com.example.product.ui.model.OrderSource
import com.example.product.ui.selectvariant.SelectVariantActivity
import com.example.product.utils.NumberUtil.formatNumber
import com.example.product.ui.dialog.BottomSheetFragmentOrderSource

class CreateOrderActivity : AppCompatActivity(), CreateOrderContracts {

    companion object{
        const val MY_PREFS:String ="MY_PREFS"
        const val ORDER_SOURCE_ID:String = "ORDER_SOURCE_ID"
    }
    private val sharedPreferences by lazy {
        getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
    }
    private var orderSourceIdToSave:Int?=null
    private var order : Order = Order()
    private lateinit var binding: ActivityCreateOrderBinding
    private lateinit var createOrderPresenter: CreateOrderPresenter
    private  var listOrderSource:MutableList<OrderSource>?=null
    private  var orderSource:OrderSource?=null
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val mListOrderLineItem = intent?.getSerializableExtra(SelectVariantActivity.KEY_LINE_ITEM) as ArrayList<OrderLineItem>
                if (order.listOrderLineItems.isEmpty()) {
                    order.listOrderLineItems = mListOrderLineItem
                } else {
                    val idsToRemove = mListOrderLineItem.map { it.variant.id }
                    order.listOrderLineItems.removeAll { idsToRemove.contains(it.variant.id) }
                    order.listOrderLineItems.addAll(0, mListOrderLineItem)

                }
                disPlayListOrder()
                displayButton()
                showInfoOrder()
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createOrderPresenter = CreateOrderPresenter(this)
        createOrderPresenter.getSourceId()
        orderSourceIdToSave = sharedPreferences.getInt(ORDER_SOURCE_ID, -1)
        onClick()

    }

    override fun callApiError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun callAPiSuccuss() {
        Toast.makeText(this, getString(R.string.tao_don_hang_thanh_cong), Toast.LENGTH_SHORT).show()
        finish()
    }


    override fun callListSourceId(orderSource: MutableList<OrderSource>) {
        listOrderSource=orderSource
    }


    private fun disPlayListOrder() {
        binding.llCreateOrderAddOrder.visibility = View.GONE
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rclvCreateOrder.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rclvCreateOrder.addItemDecoration(dividerItemDecoration)
        val adapter = OrderAdapter(order.listOrderLineItems, this)
        binding.rclvCreateOrder.adapter = adapter
        adapter.onClickItemOrder = {
            showInfoOrder()
            displayButton()
        }

    }

    private fun showInfoOrder() {
        if (order.listOrderLineItems.any { it.variant.taxable }) {
            binding.rlCreateOrderTax.visibility = View.VISIBLE
        }
        binding.tvCreateOrderTax.text = order.getTotalTaxOrder().formatNumber()
        binding.tvCreateOrderQuantity.text = order.getTotalQuantity().formatNumber()
        binding.tvCreateOrderTotalAmount.text = order.getTotalAmountOrder().formatNumber()
        binding.tvCreateOrderTotalMoney.text = order.getTotalMoney().formatNumber()
    }

    private fun onClick() {
        binding.ivCreateOrderLeft.setOnClickListener { finish() }

        binding.btnCreateOrder.setOnClickListener{ postData() }

        binding.llCreateOrderAddOrder.setOnClickListener {
            val intent = Intent(this, SelectVariantActivity::class.java)
            startForResult.launch(intent)
        }
        binding.tvCreateOrderSearch.setOnClickListener {
            val intent = Intent(this, SelectVariantActivity::class.java)
            intent.putExtra(SelectVariantActivity.KEY_LINE_ITEM, ArrayList(order.listOrderLineItems))
            startForResult.launch(intent)
        }

        binding.ivCreateOrderSettingMenu.setOnClickListener {displayBottomSheet()}

    }
    private fun postData(){
        order.sourceId = if (orderSourceIdToSave != -1) orderSourceIdToSave else listOrderSource?.get(0)?.id
        order.status="draft"
        createOrderPresenter.postOrder(order)
    }

    private fun displayBottomSheet(){
        val bottomSheet = listOrderSource?.let { it1 -> BottomSheetFragmentOrderSource(it1, sharedPreferences) }
        bottomSheet?.onClickItem= { mOrderSource ->
            orderSource=mOrderSource
            orderSourceIdToSave= orderSource!!.id
            sharedPreferences.edit().putInt(ORDER_SOURCE_ID, orderSourceIdToSave!!).apply()
            bottomSheet?.dismiss()
        }

        bottomSheet?.show(supportFragmentManager, bottomSheet.tag)
    }
    private fun displayButton() {
        if (order.listOrderLineItems.isEmpty()) {
            binding.llCreateOrderAddOrder.visibility = View.VISIBLE
            binding.btnCreateOrder.alpha = 0.5f
            binding.btnCreateOrder.isEnabled = false
        } else {
            binding.llCreateOrderAddOrder.visibility = View.GONE
            binding.btnCreateOrder.alpha = 1f
            binding.btnCreateOrder.isEnabled = true
        }
    }









}
