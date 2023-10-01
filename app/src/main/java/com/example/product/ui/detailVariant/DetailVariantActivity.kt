package com.example.product.ui.detailVariant

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.product.R
import com.example.product.databinding.ActivityDetailBinding
import com.example.product.ui.model.Product
import com.example.product.ui.model.Variant
import com.example.product.presenter.DetailVariantPresenter
import com.example.product.ui.adapter.DetailVariantAdapter
import com.example.product.utils.NumberUtil.formatNumber

class DetailVariantActivity : AppCompatActivity(),DetailVariantContracts{
    companion object{
        const val KEY_DATA_PRODUCT_ID="KEY_DATA_PRODUCT_ID"
        const val KEY_DATA_VARIANT_ID="KEY_DATA_VARIANT_ID"
    }
    private  var productId : Int =0
    private var variantId : Int = 0
    private  var product = Product()
    private  var variant=Variant()
    private  var listVariant : MutableList<Variant> = mutableListOf()
    private lateinit var adapter: DetailVariantAdapter
    private lateinit var detailVariantPresenter: DetailVariantPresenter
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productId = intent.getIntExtra(KEY_DATA_PRODUCT_ID,0)
        variantId = intent.getIntExtra(KEY_DATA_VARIANT_ID,0)
        detailVariantPresenter = DetailVariantPresenter(this)
        detailVariantPresenter.getDetailVariant(productId,variantId)
        initToolBar()
    }
    override fun onDestroy() {
        super.onDestroy()
        detailVariantPresenter.variantDisposable?.dispose()
    }
    override fun callApiError(message:String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    override fun callDetailVariant(mVariant: Variant) {
        variant = mVariant
        if(!variant.packsize){
            detailVariantPresenter.getDetailProduct(productId)
        }
        showData()

    }
    override fun callDetailProduct(mProduct: Product) {
        product = mProduct
        //getListDetailVariant()
        showData()
    }

    private fun showData(){
        getListDetailVariant()
        if(listVariant.isEmpty()){
            binding.crdDetailNameProduct.visibility = View.GONE
            binding.llDetailHeaderAddInfo.visibility = View.GONE
            binding.crdDetailStatus.visibility = View.GONE
            binding.crdDetailVariantProduct.visibility = View.GONE
            showInfo()
            showTaxPrice()
            showWarehouse()
            showCanSell()
            displayImageDetailVariant()
        }else{
            binding.rlDetailListVariant.visibility=View.VISIBLE
            binding.llDetailHeaderAddInfo.visibility = View.GONE
            binding.crdDetailVariantProduct.visibility=View.VISIBLE
            binding.llDetailAddInfo.visibility = View.GONE
            binding.crdDetailStatus.visibility = View.GONE
            binding.crdDetailNameProduct.visibility= View.GONE
            showInfo()
            showTaxPrice()
            showWarehouse()
            showCanSell()
            displayListVariantProduct()
            displayImageDetailVariant()
        }
    }
    private fun getListDetailVariant() : MutableList<Variant>{
        for(i in 0 until product.variants.size){
            if(variant.id == product.variants[i].packsizeRootId){
                listVariant.add(product.variants[i])
            }
        }
        return listVariant
    }
    private fun showInfo(){
        binding.tvDetailName.text = variant.name
        binding.tvDetailSKU.text = variant.sku
        binding.tvDetailBarcode.text = variant.barcode
        //binding.tvDetailWeight.text = variant.weightValue?.formatNoTrailingZero() +variant.weightUnit
        binding.tvDetailWeight.text=getString(R.string.khoi_luong1, variant.weightValue?.formatNumber(), variant.weightUnit)
        binding.tvDetailUnit.text=variant.getStringUnit()
        if(!variant.opt1.isNullOrEmpty()){
            binding.layoutOpt1.visibility = View.VISIBLE
            binding.tvDetailOpt1.text=variant.opt1
        }
        if(variant.packSizeQuantity!=null &&variant.packSizeQuantity!=0.0){
           binding.layoutUnitQuantity.visibility= View.VISIBLE
            binding.tvDetailQuantity.text = variant.packSizeQuantity?.formatNumber()
        }
        if(!variant.opt2.isNullOrEmpty()){
            binding.layoutOpt2.visibility = View.VISIBLE
            binding.tvDetailOpt2.text=variant.opt2
        }
        if(!variant.opt3.isNullOrEmpty()){
            binding.layoutOpt3.visibility = View.VISIBLE
            binding.tvDetailOpt3.text=variant.opt3
        }
    }
    private fun showTaxPrice(){
        if(variant.taxIncluded){
            binding.tvDetailTaxIncluded.text=getString(R.string.da_bao_gom_thue)
        }else{
            binding.tvDetailTaxIncluded.text=getString(R.string.chua_bao_gom_thue)
        }
        if(variant.taxable){
            binding.lltvDetailTax.visibility = View.VISIBLE
            binding.tvDetailInputTax.text = variant.inputVatRate?.formatNumber()
            binding.tvDetailOutputTax.text = variant.outputVatRate?.formatNumber()
        }else{
            binding.lltvDetailTax.visibility = View.GONE
        }
        binding.tvDetailRetailPrice.text=variant.getRetailPrice().formatNumber()
        binding.tvDetailImportPrice.text=variant.getImportPrice().formatNumber()
        binding.tvDetailWholesalePrice.text=variant.getWholesalePrice().formatNumber()
    }
    private fun showWarehouse(){
        binding.tvDetailOnHand.text = variant.getTotalOnhand().formatNumber()
        binding.tvDetailAvailable.text = variant.getTotalAvailable().formatNumber()
    }
    private fun showCanSell(){
        if(variant.sellable){
            binding.ivDetailSellable.setImageResource(R.drawable.ic_check_product)
            val color = ContextCompat.getColor(this, R.color.green)
            binding.ivDetailSellable.setColorFilter(color)
            binding.tvDetailSellable.text=getString(R.string.cho_phep_ban)

        }else{
            val color = ContextCompat.getColor(this, R.color.red)
            binding.ivDetailSellable.setColorFilter(color)
            binding.ivDetailSellable.setImageResource(R.drawable.ic_error)
            binding.tvDetailSellable.text=getString(R.string.khong_cho_phep_ban)
        }
    }
    private fun displayListVariantProduct(){
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rclvDetailVariantOrUnit.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rclvDetailVariantOrUnit.addItemDecoration(dividerItemDecoration)
        adapter = DetailVariantAdapter(listVariant )
        binding.rclvDetailVariantOrUnit.adapter = adapter
    }
    private fun displayImageDetailVariant(){
        if(variant.images.isNotEmpty()){
            binding.layoutImage.visibility = View.GONE
            binding.ivDetailVariant.visibility=View.VISIBLE
            Glide.with(this).load(variant.getFullPath()).into(binding.ivDetailVariant)
        }else{
            binding.ivDetailVariant.visibility=View.GONE
        }

    }
    private fun initToolBar(){
        binding.includeDetailHeader.ivToolbarLeft.visibility=View.VISIBLE
        binding.includeDetailHeader.ivToolbarLeft.setOnClickListener{
            finish()
        }
        binding.includeDetailHeader.tvToolbarTitle.text=getString(R.string.chi_tiet_phien_ban)
    }

}