package com.example.product.ui.selectvariant

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.product.ui.adapter.SelectVariantAdapter
import com.example.product.databinding.ActivitySelectVariantBinding
import com.example.product.ui.model.MetaData
import com.example.product.presenter.SelectVariantPresenter
import com.example.product.ui.createorder.CreateOrderActivity
import com.example.product.ui.model.OrderLineItem
import com.example.product.widget.PaginationScrollListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SelectVariantActivity : AppCompatActivity(), SelectVariantContracts {
    companion object {
        const val KEY_LINE_ITEM = "KEY_DATA_LINE_ITEM"
        const val SWITCH_STATE = "KEY_SWITCH_STATE"
        const val MY_PREFS = "MY_PREFS"
    }
    private val sharedPreferences by lazy {
        getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
    }

    private val searchSubject = PublishSubject.create<String>()
    private var totalVariant: Int = 0
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentPage = 1
    private var query =""
    private var orderLineItem: MutableList<OrderLineItem>? = null
    private var lineItems: MutableList<OrderLineItem>? = null
    private  var mOrderLineItem: MutableList<OrderLineItem> = mutableListOf()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivitySelectVariantBinding
    private lateinit var mSelectVariantPresenter: SelectVariantPresenter
    private lateinit var adapter: SelectVariantAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectVariantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mSelectVariantPresenter = SelectVariantPresenter(this)
        val query = binding.edtSelectVariantSearch.text.toString()
        mSelectVariantPresenter.getListVariant(currentPage, query)
        lineItems = intent.getSerializableExtra(KEY_LINE_ITEM) as? ArrayList<OrderLineItem>
        displayListVariant()
        onClick()

    }


    override fun setListVariant(listOrderLineItem: MutableList<OrderLineItem>, metaData: MetaData) {
        orderLineItem=listOrderLineItem
        if(!lineItems.isNullOrEmpty()){
            lineItems?.let { updateQuantity(it,listOrderLineItem) }
        }
        if(mOrderLineItem.isNotEmpty()){
            updateQuantity(mOrderLineItem,listOrderLineItem)
        }
        totalVariant = metaData.total
        if (currentPage == 1) {
            adapter.addList(listOrderLineItem)
        } else {
            adapter.removeFooterLoading()
            isLoading = false
            adapter.addList(listOrderLineItem)
        }
        if (!metaData.isLoadMore()) {
            isLastPage = true
        }
    }

    override fun callApiErreor(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun displayListVariant() {
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rclvSelectVariant.addItemDecoration(dividerItemDecoration)
        linearLayoutManager = LinearLayoutManager(this)
        binding.rclvSelectVariant.layoutManager = linearLayoutManager
        adapter = SelectVariantAdapter()
        binding.rclvSelectVariant.adapter = adapter

    }

    private fun loadMore() {
        binding.rclvSelectVariant.addOnScrollListener(object:
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItem() {
                adapter.addFooterLoading()
                isLoading = true
                currentPage += 1
                mSelectVariantPresenter.getListVariant(currentPage, query)
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }
        })
    }

    private fun onClick() {
        binding.ivSelectVariantLeft.setOnClickListener { finish()}
        binding.btnSelectVariantReselect.setOnClickListener {resetQuantity() }
        //binding.scSelectVariantChange.setOnClickListener { changeSwitchCompat() }
        changeSwitchCompat()
        initSearch()
        goToCreateOrder()
        loadMore()
    }
    private fun changeSwitchCompat(){
        val savedSwitchState = sharedPreferences.getBoolean(SWITCH_STATE, false)
        binding.scSelectVariantChange.isChecked = savedSwitchState
        if(savedSwitchState){
            binding.llSelectVariantBottom.visibility=View.VISIBLE
        }else{
            binding.llSelectVariantBottom.visibility=View.GONE
        }
        binding.scSelectVariantChange.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.llSelectVariantBottom.visibility = View.VISIBLE
            } else {
                binding.llSelectVariantBottom.visibility = View.GONE
            }
            sharedPreferences.edit().putBoolean(SWITCH_STATE, isChecked).apply()
        }
    }

    private fun resetQuantity(){
        for (item in mOrderLineItem) {
            item.quantity = 0.0
        }
        lineItems?.let { it1 -> updateQuantity(it1,mOrderLineItem) }
        //updateQuantity(mOrderLineItem,listOrderLineItem)
        adapter.notifyDataSetChanged()
    }

    private fun initSearch() {
        binding.edtSelectVariantSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.clearListVariant()

                query= s.toString()
                currentPage = 1
                searchSubject.onNext(query)
            }

            override fun afterTextChanged(s: Editable?) {

            }


        })
        searchSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { mSelectVariantPresenter.getListVariant(currentPage, query) }
    }

    private fun goToCreateOrder() {
        adapter.onClickItemVariant = { orderLineItem ->
            mOrderLineItem.find {it.variant.id==orderLineItem.variant.id} ?.let {
                 mOrderLineItem.remove(it)
             }

            if (binding.scSelectVariantChange.isChecked) {
                if (!mOrderLineItem.contains(orderLineItem)) {
                    mOrderLineItem.add(orderLineItem)

                }
            } else {
                mOrderLineItem.add(orderLineItem)
                val intent = Intent(this, CreateOrderActivity::class.java)
                intent.putExtra(KEY_LINE_ITEM, ArrayList(mOrderLineItem))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        binding.btnSelectVariantFinished.setOnClickListener {

            mOrderLineItem.removeAll { it.quantity == 0.0 }
            val intent = Intent(this, CreateOrderActivity::class.java)
            intent.putExtra(KEY_LINE_ITEM, ArrayList(mOrderLineItem))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun updateQuantity(sourceList:MutableList<OrderLineItem>, targetList: MutableList<OrderLineItem>) {
        for (item in sourceList) {
            val matchingItem = targetList.find {item.variant.id == it.variant.id }
            matchingItem?.quantity = item.quantity
        }
    }
}





//    private fun changeQuantity1(array1: MutableList<OrderLineItem>, listOrderLineItem: MutableList<OrderLineItem>) {
//        val intent = intent
//        val lineItems = intent.getSerializableExtra(KEY_LINE_ITEM) as? ArrayList<OrderLineItem>
//        if (lineItems != null) {
//            array1.addAll(lineItems)
//        }
//        for (item1 in array1) {
//            for (item2 in listOrderLineItem) {
//                if (item1.variant.id == item2.variant.id) {
//                    item2.quantity = item1.quantity
//                }
//            }
//        }
//
//    }


//    private fun onClickSendVariant(orderLineItem:  MutableList<OrderLineItem>){
//        val intent = Intent(this,CreateOrderActivity::class.java)
//        intent.putExtra(KEY_LISTVARIANT,ArrayList(orderLineItem))
//        setResult(Activity.RESULT_OK, intent)
//        finish()
//    }
//    private fun onClickSendListVariant(orderLineItem: MutableList<OrderLineItem>){
//        binding.btnSelectVariantFinished.setOnClickListener{
//            orderLineItem.removeAll { it.quantity == 0.0 }
//            val intent = Intent(this,CreateOrderActivity::class.java)
//            intent.putExtra(KEY_LISTVARIANT,ArrayList(orderLineItem))
//            setResult(Activity.RESULT_OK, intent)
//            finish()
//        }
//    }



