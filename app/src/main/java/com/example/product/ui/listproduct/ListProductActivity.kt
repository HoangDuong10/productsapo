package com.example.product.ui.listproduct

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
import com.example.product.ui.adapter.ProductAdapter
import com.example.product.databinding.ActivityListProductBinding
import com.example.product.presenter.ListProductPresenter
import com.example.product.ui.detailProduct.DetailProductActivity
import com.example.product.ui.detailVariant.DetailVariantActivity
import com.example.product.ui.model.MetaData
import com.example.product.ui.model.Product
import com.example.product.ui.model.Variant
import com.example.product.widget.PaginationScrollListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ListProductActivity : AppCompatActivity(), ListProductContracts {
    private var query = ""
    private var totalProduct: Int = 0
    private var totalVariant: Int = 0
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var checkType :Boolean = true
    private var currentPage = 1
    private val searchSubject = PublishSubject.create<String>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var productAdapter: ProductAdapter
    private lateinit var mListProductPresenter: ListProductPresenter
    private lateinit var binding: ActivityListProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mListProductPresenter = ListProductPresenter(this)
        mListProductPresenter.getListProduct(currentPage,query)
        initToolBar(resources.getString(R.string.san_pham_xml), R.drawable.ic_inventory)
        displayListProduct()
        onClick()
    }


    override fun onDestroy() {
        super.onDestroy()
        mListProductPresenter.disposable?.dispose()
    }

    override fun callListProduct(listProduct: MutableList<Product>, metaData: MetaData) {
        totalProduct = metaData.total
        if(totalProduct==0){
            binding.llListProductNoResult.visibility=View.VISIBLE
            binding.rclvListProduct.visibility=View.GONE
        }else{
            binding.llListProductNoResult.visibility=View.GONE
            binding.rclvListProduct.visibility=View.VISIBLE
        }
        if (currentPage ==1) {
            binding.tvListProductTotal.text = resources.getString(R.string.san_pham, totalProduct.toString())
            productAdapter.addListProduct(listProduct)
        }else{
            productAdapter.remoteFooterLoading()
            isLoading = false
            productAdapter.addListProduct(listProduct)
        }
        if (!metaData.isLoadMore()) {
            isLastPage = true
        }

    }

    override fun callListVariant(listVariant: MutableList<Variant>, metaData: MetaData) {
        totalVariant = metaData.total
        if(totalVariant==0){
            binding.llListProductNoResult.visibility=View.VISIBLE
            binding.rclvListProduct.visibility=View.GONE
        }else{
            binding.llListProductNoResult.visibility=View.GONE
            binding.rclvListProduct.visibility=View.VISIBLE
        }
        if (currentPage == 1) {
            productAdapter.addListProduct(listVariant)
            binding.tvListProductTotal.text = getString(R.string.phien_ban,totalVariant.toString())
        }else{
            productAdapter.remoteFooterLoading()
            isLoading = false
            productAdapter.addListProduct(listVariant)
        }
        if (!metaData.isLoadMore()) {
            isLastPage = true
        }
    }

    override fun callApiError(message:String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
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


    private fun loadMore() {
        binding.rclvListProduct.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItem() {
                productAdapter.addFooterLoading()
                isLoading = true
                currentPage += 1
                if (productAdapter.modeView == ProductAdapter.TYPE_PRODUCT) {
                    mListProductPresenter.getListProduct(currentPage,query)
                } else {
                    mListProductPresenter.getListVariant(currentPage,query)
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
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        productAdapter= ProductAdapter()
        binding.rclvListProduct.apply {
            layoutManager=linearLayoutManager
            adapter=productAdapter
            addItemDecoration(dividerItemDecoration)
        }

    }

    private fun refreshData() {
        binding.srListProduct.setOnRefreshListener {
            currentPage = 1
            productAdapter.clearListProduct()
            if (productAdapter.modeView== ProductAdapter.TYPE_PRODUCT) {
                val query=binding.edtListProductSearch.text.toString()
                mListProductPresenter.getListProduct(currentPage,query)
            } else {
                val query=binding.edtListProductSearch.text.toString()
                mListProductPresenter.getListVariant(currentPage,query)
            }
            binding.srListProduct.isRefreshing = false
            isLastPage = false
        }
    }

    private fun onClick(){
        binding.includeListProductHeader.ivEditOrProductOrVariant.setOnClickListener { changeTypeProductOrVariant() }
        goToDetailProduct()
        goToDetailVariant()
        refreshData()
        loadMore()
        initSearch()
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


    private fun initSearch() {
        binding.edtListProductSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                productAdapter.clearListProduct()
                currentPage = 1
                //isLoading = false
                query = s.toString()
                //searchSubject.onNext(query)
                if (productAdapter.modeView == ProductAdapter.TYPE_PRODUCT) {
                    mListProductPresenter.getListProduct(currentPage, query)
                } else {
                    mListProductPresenter.getListVariant(currentPage, query)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

//        searchSubject
//            .debounce(200, TimeUnit.MILLISECONDS)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {productAdapter.clearListProduct()
//                if (productAdapter.modeView == ProductAdapter.TYPE_PRODUCT) {
//                mListProductPresenter.getListProduct(currentPage, query)
//            } else {
//                mListProductPresenter.getListVariant(currentPage, query)
//            } }
    }

    private fun changeTypeProductOrVariant() {
        productAdapter.clearListProduct()
        isLastPage = false
        currentPage = 1
        checkType = !checkType
        if (checkType) {
            initToolBar(resources.getString(R.string.san_pham_xml), R.drawable.ic_inventory)
            productAdapter.modeView=ProductAdapter.TYPE_PRODUCT
            mListProductPresenter.getListProduct(currentPage,query)

        } else{
            initToolBar(resources.getString(R.string.quan_li_kho), R.drawable.ic_product_management)
            productAdapter.modeView=ProductAdapter.TYPE_VARIANT
            mListProductPresenter.getListVariant(currentPage,query)


        }
    }

}