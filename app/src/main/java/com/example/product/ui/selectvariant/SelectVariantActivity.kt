package com.example.product.ui.selectvariant

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.product.R
import com.example.product.ui.adapter.SelectVariantAdapter
import com.example.product.databinding.ActivitySelectVariantBinding
import com.example.product.ui.model.MetaData
import com.example.product.ui.model.Variant
import com.example.product.presenter.SelectVariantPresenter
import com.example.product.ui.createorder.CreateOrderActivity
import com.example.product.ui.AppConfig
import com.example.product.ui.adapter.ProductAdapter
import com.example.product.widget.PaginationScrollListener

class SelectVariantActivity : AppCompatActivity(), SelectVariantContracts {
    companion object{
        const val KEY_LISTVARIANT="KEY_LISTVARIANT"

    }
    private var totalVariant: Int = 0
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentPage = 1
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivitySelectVariantBinding
    private lateinit var mlistVariant : MutableList<Variant>
    private lateinit var listTotal : MutableList<String>
    private lateinit var mSelectVariantPresenter: SelectVariantPresenter
    private lateinit var adapter: SelectVariantAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectVariantBinding.inflate(layoutInflater)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rclvSelectVariant.addItemDecoration(dividerItemDecoration)
        mSelectVariantPresenter = SelectVariantPresenter(this)
        mSelectVariantPresenter.getListVariant(currentPage)
        mlistVariant = mutableListOf()
        listTotal = mutableListOf()
        backPressed()
        search()
        setContentView(binding.root)
    }


    override fun setListVariant(mListVariant: MutableList<Variant>, metaData: MetaData) {
        totalVariant = metaData.total!!
        if (currentPage == 1) {
            displayListVariant(mListVariant)
        } else {
            adapter.removeFooterLoading()
            isLoading = false
            adapter.addList(mListVariant)
        }
        if (currentPage < currentPage * AppConfig.limit) {
            adapter.addFooterLoading()
        } else {
            isLastPage = true
        }
    }

    override fun callApiErreor() {
        Toast.makeText(this,getString(R.string.call_api_khong_thanh_cong), Toast.LENGTH_SHORT).show()
    }

    private fun displayListVariant(listVariant: MutableList<Variant>) {
        linearLayoutManager = LinearLayoutManager(this)
        binding.rclvSelectVariant.layoutManager = linearLayoutManager
        adapter = SelectVariantAdapter(listVariant, this)
        binding.rclvSelectVariant.adapter = adapter
        goToCreateOrder()
        pagination()
    }

    private fun pagination() {
        binding.rclvSelectVariant.addOnScrollListener(object :
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
    private fun onClickSendVariant(variant: MutableList<Variant>){
        val intent = Intent(this,CreateOrderActivity::class.java)
        intent.putExtra(KEY_LISTVARIANT,ArrayList(variant))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    private fun onClickSendListVariant(variant: MutableList<Variant>){
        binding.btnSelectVariantFinished.setOnClickListener{
            val intent = Intent(this,CreateOrderActivity::class.java)
            intent.putExtra(KEY_LISTVARIANT,ArrayList(variant))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
    private fun backPressed() {
        binding.ivSelectVariantLeft.setOnClickListener {
            finish()
        }
    }
    private fun goToCreateOrder(){
        adapter.onClickItemVariant= { idVariant, variant ->
            if (binding.scSelectVariantChange.isChecked) {
                binding.llSelectVariantBottom.visibility=View.VISIBLE
                if (!mlistVariant.contains(variant)) {
                    variant.total = 1.0
                    mlistVariant.add(variant)
                } else {
                    val vari = mlistVariant.firstOrNull { it.id == idVariant }
//                        var index = mlistVariant.indexOfFirst { it.id == id }
                    if (vari != null) {
                        vari.total = vari.total!! + 1
                    }
                }
                onClickSendListVariant(mlistVariant)
            } else {
                binding.llSelectVariantBottom.visibility=View.GONE
                variant.total = 1.0
                mlistVariant.add(variant)
                onClickSendVariant(mlistVariant)
            }
        }
    }
    private fun search(){
        binding.edtSelectVariantSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val content : String = s.toString()
                mSelectVariantPresenter.findVariant(content)
            }
            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

}

