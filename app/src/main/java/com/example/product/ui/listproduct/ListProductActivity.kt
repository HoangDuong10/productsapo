package com.example.product.ui.listproduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.product.R
import com.example.product.adapter.ProductAdapter
import com.example.product.adapter.VariantAdapter
import com.example.product.databinding.ActivityListProductBinding
import com.example.product.listense.IClickItemProduct
import com.example.product.listense.IClickItemVariant
import com.example.product.model.MetaData
import com.example.product.model.Product
import com.example.product.model.Variants
import com.example.product.ui.detailProduct.DetailProductActivity
import com.example.product.ui.detailVariant.DetailVariantActivity
import com.example.product.utils.Constant
import com.example.product.utils.GlobalFuntion
import com.example.product.utils.PaginationScrollListener
import kotlin.math.ceil

class ListProductActivity : AppCompatActivity(),ListProductContracts {
    private var listVariant : MutableList<Variants>?=null
    private var listProduct : MutableList<Product>?=null
    private var totalProduct : Int = 0
    private var totalVariant : Int = 0
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var totalPage = 0
    private var currentPage = 1;
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var productAdapter: ProductAdapter
    private lateinit var variantAdapter : VariantAdapter
    private lateinit var mListProductPresenter: ListProductPresenter
    private lateinit var binding : ActivityListProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        var dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rcvProduct.addItemDecoration(dividerItemDecoration)
        mListProductPresenter = ListProductPresenter(this)
        mListProductPresenter.getListProduct(currentPage)
        clickOnChangeAdapter()
        setContentView(binding.root)
    }
    fun initToolBar(title: String,image: Int){
        binding.header.imgLeft.visibility = View.VISIBLE
        binding.tvName.text = title
        binding.header.tvTitle.visibility= View.GONE
        binding.header.imgSettingMenu.setImageResource(R.drawable.ic_setting)
        binding.header.imgSettingMenu.visibility = View.VISIBLE
        binding.header.imgProductOrEditOrVariant.visibility = View.VISIBLE
        binding.header.imgProductOrEditOrVariant.setImageResource(image)
        binding.header.imgLeft.setOnClickListener{
            finish()
        }
    }
    override fun callApiErreor() {
        GlobalFuntion.showToastMessage(this,"Call api không thành công")
    }

    override fun setListProduct(mListProduct: MutableList<Product>, metadata: MetaData) {
        listProduct = mListProduct
        totalProduct = metadata.total
        binding.tvTotal.text = totalProduct.toString()+ " Sản phẩm"
        initToolBar("Sản phẩm",R.drawable.ic_inventory)
        totalPage = ceil((metadata.total.toDouble() / Constant.limit)).toInt()
        Log.d("aaa", "" + totalPage)
        if (currentPage == 1) {
            displayListProduct(mListProduct)
        } else {
            productAdapter.remoteFooterLoading()
            isLoading = false
            productAdapter.setList(mListProduct)
            productAdapter.notifyDataSetChanged()
        }
        if (currentPage < totalPage) {
            productAdapter.addFooterLoading()
        } else {
            isLastPage = true
        }

    }

    override fun setListVariant(mListVariant: MutableList<Variants>, metaData: MetaData) {
        listVariant=mListVariant
        totalVariant = metaData.total
        binding.tvTotal.text = totalVariant.toString()+ " Phiên bản"
        initToolBar("Quản lí kho",R.drawable.ic_product_management)
        totalPage = ceil(metaData.total.toDouble()/Constant.limit).toInt()
        if(currentPage==1){
            displayListVariant(mListVariant)
        }else{
            variantAdapter.removeFooterLoading()
            isLoading= false
            variantAdapter.setList(mListVariant)
            variantAdapter.notifyDataSetChanged()
        }
        if(currentPage<totalPage){
            variantAdapter.addFooterLoading()
        }else{
            isLastPage= true
        }
    }
    fun clickOnChangeAdapter(){
        binding.header.imgProductOrEditOrVariant.setOnClickListener{
            if(binding.rcvProduct.adapter is ProductAdapter){
                binding.tvTotal.text = totalVariant.toString()+" Phiên bản"
                isLastPage=false
                currentPage = 1
                mListProductPresenter.getListVariant(currentPage)
            }else {
                binding.tvTotal.text = totalProduct.toString()+ " Sản phẩm"
                isLastPage=false
                currentPage=1
                mListProductPresenter.getListProduct(currentPage)
            }

        }
    }
    fun pagination(){
        binding.rcvProduct.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager){
            override fun loadMoreItem() {
                isLoading = true
                currentPage+=1
                if(binding.rcvProduct.adapter is ProductAdapter){
                    mListProductPresenter.getListProduct(currentPage)
                }else if(binding.rcvProduct.adapter is VariantAdapter){
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
    fun displayListVariant(listVariant:MutableList<Variants>){
        linearLayoutManager = LinearLayoutManager(this)
        binding.rcvProduct.layoutManager = linearLayoutManager
        variantAdapter = VariantAdapter(listVariant,this,object :IClickItemVariant{
            override fun onClickItemProduct(productId: Int, variantInt: Int) {
                goToDetailVariant(productId,variantInt)
            }
        })
        binding.rcvProduct.adapter= variantAdapter
        pagination()
        refreshData()
    }
    fun displayListProduct(listProduct: MutableList<Product>) {
        linearLayoutManager = LinearLayoutManager(this)
        binding.rcvProduct.layoutManager = linearLayoutManager
        productAdapter = ProductAdapter(listProduct,this,object : IClickItemProduct{
            override fun onClickItemProduct(id: Int) {
                goToDetailProduct(id)
            }

        })
        binding.rcvProduct.adapter = productAdapter
        pagination()
        refreshData()
    }
    fun refreshData() {
        binding.swipeRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                currentPage = 1
                if(binding.rcvProduct.adapter is ProductAdapter){
                    productAdapter.clearListProduct()
                    mListProductPresenter.getListProduct(currentPage)
                }else {
                    variantAdapter.clearListVariant()
                    mListProductPresenter.getListVariant(currentPage)

                }


                binding.swipeRefresh.isRefreshing = false
                isLastPage = false
            }

        })
    }
    fun goToDetailVariant(productID: Int,variantId:Int) {
        var intent = Intent(this, DetailVariantActivity::class.java)
        intent.putExtra("product_id", productID)
        intent.putExtra("variant_id",variantId)
        startActivity(intent)
    }
    fun goToDetailProduct(id: Int) {
        var intent = Intent(this, DetailProductActivity::class.java)
        intent.putExtra("product_id", id)
        startActivity(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        mListProductPresenter.productDisposable?.dispose()
        mListProductPresenter.variantDisposable?.dispose()
    }

}