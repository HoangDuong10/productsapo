package com.example.product.ui.listproduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.product.R
import com.example.product.ui.adapter.ProductAdapter
import com.example.product.databinding.ActivityListProductBinding
import com.example.product.presenter.ListProductPresenter
import com.example.product.ui.AppConfig
import com.example.product.ui.detailProduct.DetailProductActivity
import com.example.product.ui.detailVariant.DetailVariantActivity

import com.example.product.ui.model.MetaData
import com.example.product.ui.model.Product
import com.example.product.ui.model.Variant
import com.example.product.widget.PaginationScrollListener

class ListProductActivity : AppCompatActivity(), ListProductContracts {
    private var listVariant: MutableList<Variant>? = null
    private var listProduct: MutableList<Product>? = null
    private var totalProduct: Int = 0
    private var totalVariant: Int = 0
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var totalPage = 0
    private var currentPage = 1;
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var productAdapter: ProductAdapter
    private lateinit var mListProductPresenter: ListProductPresenter
    private lateinit var binding: ActivityListProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        mListProductPresenter = ListProductPresenter(this)
        mListProductPresenter.getListProduct(currentPage)
        clickOnChangeTypeAdapter()
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rclvListProduct.addItemDecoration(dividerItemDecoration)
        search()
        setContentView(binding.root)
    }
    override fun onDestroy() {
        super.onDestroy()
        mListProductPresenter.productDisposable?.dispose()
    }

    override fun callListProduct(mListProduct: MutableList<Product>, metadata: MetaData) {
        listProduct = mListProduct
        totalProduct = metadata.total!!
        binding.tvListProductTotal.text = totalProduct.toString()+AppConfig.Space + resources.getString(R.string.san_pham)
        initToolBar(resources.getString(R.string.san_pham), R.drawable.ic_inventory)
        Log.d("aaa", "" + totalPage)
        if (currentPage == 1) {
            displayListProduct()
            productAdapter.setData(ProductAdapter.TYPE_PRODUCT,mListProduct)
        } else {
            productAdapter.remoteFooterLoading()
            isLoading = false
            productAdapter.addListProduct(mListProduct)
        }
        if (totalProduct>currentPage* AppConfig.limit) {
            productAdapter.addFooterLoading()
        } else {
            isLastPage = true
        }

    }

    override fun callListVariant(mListVariant: MutableList<Variant>, metaData: MetaData) {
        listVariant = mListVariant
        totalVariant = metaData.total!!
        if(totalVariant==0){
            binding.llListProductNoResult.visibility=View.VISIBLE
            binding.rclvListProduct.visibility=View.GONE
        }else{
            binding.llListProductNoResult.visibility=View.GONE
            binding.rclvListProduct.visibility=View.VISIBLE
        }
        binding.tvListProductTotal.text = totalVariant.toString()+AppConfig.Space + resources.getString(R.string.phien_ban)
        initToolBar(resources.getString(R.string.quan_li_kho), R.drawable.ic_product_management)
        if (currentPage == 1) {
            displayListProduct()
            productAdapter.setData(ProductAdapter.TYPE_VARIANT,mListVariant)
        } else {
            productAdapter.remoteFooterLoading()
            isLoading = false
            productAdapter.addListProduct(mListVariant)
        }
        if (totalVariant>currentPage* AppConfig.limit) {
            productAdapter.addFooterLoading()
        } else {
            isLastPage = true
        }
    }

    override fun callApiErreor() {
        Toast.makeText(this,getString(R.string.call_api_khong_thanh_cong), Toast.LENGTH_SHORT).show()
    }

    private fun initToolBar(title: String, image: Int) {
        binding.includeListProductHeader.ivToolbarLeft.visibility = View.VISIBLE
        binding.tvListProductName.text = title
        binding.includeListProductHeader.tvToolbarTitle.visibility = View.GONE
        binding.includeListProductHeader.ivToolbarSettingMenu.setImageResource(R.drawable.ic_setting)
        binding.includeListProductHeader.ivToolbarSettingMenu.visibility = View.VISIBLE
        binding.includeListProductHeader.ivEditOrProductOrVariant.visibility = View.VISIBLE
        binding.includeListProductHeader.ivEditOrProductOrVariant.setImageResource(image)

        binding.includeListProductHeader.ivToolbarLeft.setOnClickListener {
            finish()
        }
    }

    private fun clickOnChangeTypeAdapter() {
        binding.includeListProductHeader.ivEditOrProductOrVariant.setOnClickListener {
            if (productAdapter.getCurrentType()==ProductAdapter.TYPE_PRODUCT) {
                binding.tvListProductTotal.text = totalVariant.toString() + resources.getString(R.string.phien_ban)
                isLastPage = false
                currentPage = 1
                mListProductPresenter.getListVariant(currentPage)
            } else if(productAdapter.getCurrentType()==ProductAdapter.TYPE_VARIANT){
                binding.tvListProductTotal.text = totalProduct.toString() + resources.getString(R.string.san_pham)
                isLastPage = false
                currentPage = 1
                mListProductPresenter.getListProduct(currentPage)
            }
        }
    }

    private fun pagination() {
        binding.rclvListProduct.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItem() {
                isLoading = true
                currentPage += 1
                if (productAdapter.getCurrentType() == ProductAdapter.TYPE_PRODUCT) {
                    mListProductPresenter.getListProduct(currentPage)
                } else if (productAdapter.getCurrentType() == ProductAdapter.TYPE_VARIANT) {
                    mListProductPresenter.getListVariant(currentPage)
                }
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }
        })
    }

    private fun displayListProduct() {
        linearLayoutManager = LinearLayoutManager(this)
        binding.rclvListProduct.layoutManager = linearLayoutManager
        productAdapter = ProductAdapter(this)
        binding.rclvListProduct.adapter = productAdapter
        goToDetailProduct()
        goToDetailVariant()
        pagination()
        refreshData()
    }

    private fun refreshData() {
        binding.srListProduct.setOnRefreshListener {
            currentPage = 1
            productAdapter.clearListProduct()
            if (productAdapter.getCurrentType() == ProductAdapter.TYPE_PRODUCT) {
                mListProductPresenter.getListProduct(currentPage)
            } else {
                mListProductPresenter.getListVariant(currentPage)
            }
            binding.srListProduct.isRefreshing = false
            isLastPage = false
        }
    }

    private fun goToDetailVariant() {
        productAdapter.onClickItemVariant={idProduct,idVariant->
            val intent = Intent(this, DetailVariantActivity::class.java)
            intent.putExtra(DetailProductActivity.KEY_DATA_PRODUCT_ID, idProduct)
            intent.putExtra(DetailProductActivity.KEY_DATA_VARIANT_ID, idVariant)
            startActivity(intent)
        }
    }

    private fun goToDetailProduct() {
        productAdapter.onClickItemProduct= {id->
            val intent = Intent(this, DetailProductActivity::class.java)
            intent.putExtra(DetailProductActivity.KEY_DATA_PRODUCT_ID, id)
            startActivity(intent)
        }
    }
    private fun search(){
        binding.edtListProductSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val content : String = s.toString()
                if(productAdapter.getCurrentType()==ProductAdapter.TYPE_PRODUCT){
                    mListProductPresenter.findProduct(content)
                }else{
                    mListProductPresenter.findVariant(content)
                }

            }
            override fun afterTextChanged(s: Editable?) {

            }

        })
    }


}