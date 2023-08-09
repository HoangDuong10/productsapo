package com.example.product.ui.selectvariant

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.product.adapter.SelectVariantAdapter
import com.example.product.adapter.VariantAdapter
import com.example.product.databinding.ActivitySelectVariantBinding
import com.example.product.listense.IClickItemSelectVariant
import com.example.product.listense.IClickItemVariant
import com.example.product.model.MetaData
import com.example.product.model.Variants
import com.example.product.ui.createorder.CreateOrderActivity
import com.example.product.utils.Constant
import com.example.product.utils.GlobalFuntion
import com.example.product.utils.PaginationScrollListener
import kotlin.math.ceil

class SelectVariantActivity : AppCompatActivity(), SelectVariantContracts {
    private lateinit var mlistVariant : MutableList<Variants>
    private lateinit var listTotal : MutableList<String>
    private lateinit var mSelectVariantPresenter: SelectVariantPresenter
    private lateinit var adapter: SelectVariantAdapter
    private var totalVariant: Int = 0
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var totalPage = 0
    private var currentPage = 1;
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivitySelectVariantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectVariantBinding.inflate(layoutInflater)
        mSelectVariantPresenter = SelectVariantPresenter(this)
        mSelectVariantPresenter.getListVariant(currentPage)
        mlistVariant = mutableListOf()
        listTotal = mutableListOf()
        backPressed()
        setContentView(binding.root)
    }

    override fun callApiErreor() {
        GlobalFuntion.showToastMessage(this, "Call api không thành công")
    }

    override fun setListVariant(mListVariant: MutableList<Variants>, metaData: MetaData) {
        totalVariant = metaData.total
        totalPage = ceil(metaData.total.toDouble() / Constant.limit).toInt()
        if (currentPage == 1) {
            displayListVariant(mListVariant)
        } else {
            adapter.removeFooterLoading()
            isLoading = false
            adapter.setList(mListVariant)
            adapter.notifyDataSetChanged()
        }
        if (currentPage < totalPage) {
            adapter.addFooterLoading()
        } else {
            isLastPage = true
        }
    }

    fun displayListVariant(listVariant: MutableList<Variants>) {
        linearLayoutManager = LinearLayoutManager(this)
        binding.rcvVariant.layoutManager = linearLayoutManager
        var dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rcvVariant.addItemDecoration(dividerItemDecoration)
        adapter = SelectVariantAdapter(listVariant, this,object : IClickItemSelectVariant{
            override fun onClickItemSelectVariant(variant: Variants,id:Int) {
                if(binding.switchChange.isChecked){
                    if(!mlistVariant.contains(variant)){
                        variant.total=1
                        mlistVariant?.add(variant)
                    }else{
                        if(mlistVariant.filter { s-> s.id ==id }.first()!=null){
                            mlistVariant.filter { s-> s.id ==id }.first().total=mlistVariant.filter { s-> s.id ==id }.first().total!!+1
                        }
                    }
                    onClickSendListVariant(mlistVariant)
                }else{
                    variant.total=1
                    mlistVariant?.add(variant)
                    onClickSendVariant(mlistVariant)
                }
            }
        })
        binding.rcvVariant.adapter = adapter
        pagination()
    }

    fun pagination() {
        binding.rcvVariant.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItem() {
                isLoading = true
                currentPage += 1
                mSelectVariantPresenter.getListVariant(currentPage)
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }
        })
    }
    fun onClickSendVariant(variant: MutableList<Variants>){
        val intent = Intent(this,CreateOrderActivity::class.java)
        intent.putExtra("list_variant",ArrayList(variant))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    fun onClickSendListVariant(variant: MutableList<Variants>){
        binding.btnFinished.setOnClickListener{
            val intent = Intent(this,CreateOrderActivity::class.java)
            intent.putExtra("list_variant",ArrayList(variant))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
    fun backPressed() {
        binding.imgLeft.setOnClickListener {
            finish()
        }
    }

}

