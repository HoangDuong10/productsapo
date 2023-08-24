package com.example.product.ui.detailProduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.product.R
import com.example.product.databinding.ActivityDetailBinding


import com.example.product.ui.adapter.ImageAdapter
import com.example.product.ui.model.Product
import com.example.product.presenter.DetailProductPresenter

import com.example.product.ui.adapter.DetailProductAdapter
import com.example.product.ui.detailVariant.DetailVariantActivity
import com.example.product.utils.NumberUtil.formatNoTrailingZero

class DetailProductActivity : AppCompatActivity(),DetailProductContracts {
    companion object{
        const val KEY_DATA_PRODUCT_ID="KEY_DATA_PRODUCT_ID"
        const val KEY_DATA_VARIANT_ID="KEY_DATA_VARIANT_ID"
    }
    private lateinit var product: Product
    private lateinit var detailProductAdapter: DetailProductAdapter
    private lateinit var imageAdapter : ImageAdapter
    private lateinit var mDetailProductPresenter: DetailProductPresenter
    private lateinit var  binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolBar()
        val productId = intent.getIntExtra(KEY_DATA_PRODUCT_ID,0)
        mDetailProductPresenter = DetailProductPresenter(this)
        mDetailProductPresenter.getDetailProduct(productId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDetailProductPresenter.disposable?.dispose()
    }

    override fun callProduct(mProduct: Product) {
        product = mProduct
        if(product.variants[0].name.equals(product.name)&& product.variants.size==1&&product.variants[0].isOtp()){
            showInfo()
            showTaxPrice()
            showWarehouse()
            showInfoAdd()
            showStatus()
            showListImage()
            binding.crdDetailNameProduct.visibility = View.GONE
            binding.crdDetailVariantProduct.visibility = View.GONE
        }else{
            binding.crdDetailNameProduct.visibility = View.VISIBLE
            binding.crdDetailInfo.visibility = View.GONE
            binding.crdDetailTaxPrice.visibility =View.GONE
            binding.cardViewWarehouse.visibility = View.GONE
            binding.tvDetailNameProduct.text = product.name
            showInfoAdd()
            showListImage()
            showStatus()
            displayListVariantProduct()
        }
    }
    override fun callApiErreor() {
        Toast.makeText(this,getString(R.string.call_api_khong_thanh_cong), Toast.LENGTH_SHORT).show()
    }

    private fun showInfo(){
        binding.tvDetailName.text = product.name
        binding.tvDetailSKU.text = product.variants[0].sku
        binding.tvDetailBarcode.text = product.variants[0].barcode
        val weightText = (product.variants[0].weightValue)?.formatNoTrailingZero()+product.variants[0].weightUnit
        binding.tvDetailWeight.text =weightText
        binding.tvDetailUnit.text = product.variants[0].getStringUnit()
    }

    private fun showTaxPrice(){
        binding.tvDetailTaxIncluded.text = if(product.variants[0].taxIncluded){
            getString(R.string.da_bao_gom_thue)
        } else {
            getString(R.string.chua_bao_gom_thue)
        }
        if(product.variants[0].taxable){
            binding.lltvDetailTax.visibility = View.VISIBLE
            binding.tvDetailInputTax.text = product.variants[0].getInputVatRate()
            binding.tvDetailOutputTax.text = product.variants[0].getOutputVatRate()
        }else{
            binding.lltvDetailTax.visibility = View.GONE
        }
        binding.tvDetailWholesalePrice.text=product.variants[0].getWholesalePrice().formatNoTrailingZero()
        binding.tvDetailImportPrice.text = product.variants[0].getImportPrice().formatNoTrailingZero()
        binding.tvDetailRetailPrice.text=product.variants[0].getRetailPrice().formatNoTrailingZero()
    }

    private fun showWarehouse(){
        binding.tvDetailOnHand.text = product.variants[0].getTotalOnhand().formatNoTrailingZero()
        binding.tvDetailAvailable.text = product.variants[0].getTotalAvailable().formatNoTrailingZero()
    }
    private fun onClickInfoAdd(){
        binding.rlDetailAddInfo.setOnClickListener{
            if(binding.llDetailAddInfo.visibility== View.VISIBLE){
                binding.llDetailAddInfo.visibility = View.GONE
                binding.ivDetailDownOrUp.setImageResource(R.drawable.ic_down)
            }else{
                binding.llDetailAddInfo.visibility = View.VISIBLE
                binding.ivDetailDownOrUp.setImageResource(R.drawable.ic_up)
            }
        }
    }
    private fun showInfoAdd(){
        binding.tvDetailProductCategory.text= product.getStringCategory()
        binding.tvDetailBrand.text = product.getStringBrand()
        binding.tvDetailDescription.text=product.getStringDescription()
        if(product.variants[0].sellable){
            binding.ivDetailSellable.setImageResource(R.drawable.ic_check)
            val color = ContextCompat.getColor(this, R.color.green)
            binding.ivDetailSellable.setColorFilter(color)
            binding.tvDetailSellable.text=getString(R.string.cho_phep_ban)
        }else{
            binding.ivDetailSellable.setImageResource(R.drawable.ic_error)
            binding.tvDetailSellable.text=getString(R.string.khong_cho_phep_ban)
        }
        onClickInfoAdd()
    }
    private fun showStatus(){
        if(product.isStatus()){
            val color = ContextCompat.getColor(this, R.color.green)
            binding.ivDetailStatus.setColorFilter(color)
            binding.tvDetailStatus.text = getString(R.string.dang_giao_dich)
        }else{
            val color = ContextCompat.getColor(this, R.color.orange)
            binding.ivDetailStatus.setColorFilter(color)
            binding.tvDetailStatus.text = getString(R.string.ngung_giao_dich)
        }
    }
    private fun displayListVariantProduct(){
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rclvDetailVariantOrUnit.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        binding.rclvDetailVariantOrUnit.addItemDecoration(dividerItemDecoration)
        detailProductAdapter = DetailProductAdapter(product.variants,this)
        binding.rclvDetailVariantOrUnit.adapter = detailProductAdapter
        goToDetailVariant()
    }
    private fun displayListImageProduct(){
        val linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.rclvDetailImage.layoutManager = linearLayoutManager
        imageAdapter = ImageAdapter(product.images, this)
        binding.rclvDetailImage.adapter = imageAdapter
    }
    private fun initToolBar(){
        binding.includeDetailHeader.ivToolbarLeft.visibility=View.VISIBLE
        binding.includeDetailHeader.ivToolbarLeft.setOnClickListener{
            finish()
        }
        binding.includeDetailHeader.tvToolbarTitle.text=getString(R.string.chi_tiet_san_pham)
    }
    private fun showListImage(){
        if(product.images.isEmpty()){
            binding.rclvDetailImage.visibility = View.VISIBLE
        }else{
            displayListImageProduct()
        }
    }
    private fun goToDetailVariant() {
        detailProductAdapter.onClickItemVariant={idProduct,idVariant->
            var intent = Intent(this, DetailVariantActivity::class.java)
            intent.putExtra(DetailProductActivity.KEY_DATA_PRODUCT_ID, idProduct)
            intent.putExtra(DetailProductActivity.KEY_DATA_VARIANT_ID, idVariant)
            startActivity(intent)
        }
    }
}