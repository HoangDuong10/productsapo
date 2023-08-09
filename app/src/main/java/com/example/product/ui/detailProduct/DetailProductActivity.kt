package com.example.product.ui.detailProduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.product.R
import com.example.product.adapter.DetailProductAdapter
import com.example.product.adapter.ImageAdapter
import com.example.product.databinding.ActivityDetailProductOrVariantBinding

import com.example.product.model.Product
import com.example.product.utils.GlobalFuntion

class DetailProductActivity : AppCompatActivity(),DetailProductContracts {
    private lateinit var product: Product
    private lateinit var detailProductAdapter: DetailProductAdapter
    private lateinit var imageAdapter : ImageAdapter
    private lateinit var mDetailProductPresenter: DetailProductPresenter
    private lateinit var  binding: ActivityDetailProductOrVariantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductOrVariantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolBar()
        var productId = getIntent().getIntExtra("product_id",0)
        mDetailProductPresenter = DetailProductPresenter(this)
        mDetailProductPresenter.getDetailProduct(productId)

    }

    override fun callApiErreor() {
        GlobalFuntion.showToastMessage(this,"Call api không thành công")
    }

    override fun setData(mProduct: Product) {
        product = mProduct
        if(product.variants[0].name.equals(product.name)&& product.variants.size==1){
            showInfo()
            showTaxPrice()
            showWarehouse()
            showInfoAdd()
            showStatus()
            show()
            binding.cardViewNameProduct.visibility = View.GONE
            binding.cardViewVariantProduct.visibility = View.GONE
        }else{
            binding.cardViewNameProduct.visibility = View.VISIBLE
            binding.cardViewInfo.visibility = View.GONE
            binding.cardViewTaxPrice.visibility =View.GONE
            binding.cardViewWarehouse.visibility = View.GONE
            binding.tvNameProduct.text = product.name
            showInfoAdd()
            show()
            showStatus()
            displayListVariantProduct()
        }
    }
    fun showInfo(){
        binding.tvName.text = product.name
        binding.tvSku.text = product.variants[0].sku
        binding.tvBarCode.text = product.variants[0].barcode
        binding.tvWeight.text = product.variants[0].weight_value.toString()+product.variants[0].weight_unit
        if(product.variants[0].unit.isNullOrEmpty()){
            binding.tvUnit.text="---"
        }else{
            binding.tvUnit.text = product.variants[0].unit
        }
    }
    fun showTaxPrice(){
        binding.tvInputTax.text = GlobalFuntion.removeDecimal(product.variants[0].input_vat_rate)
        binding.tvOutputTax.text = GlobalFuntion.removeDecimal(product.variants[0].output_vat_rate)
        for(i in 0 until product.variants[0].variant_prices.size){
            if(product.variants[0].variant_prices[i].price_list.code.equals("BANLE")){
                binding.tvRetailPrice.text = GlobalFuntion.removeDecimal(product.variants[0].variant_prices[i].value)
            } else if (product.variants[0].variant_prices[i].price_list.code.equals("GIANHAP")){
                binding.tvImportPrice.text = GlobalFuntion.removeDecimal(product.variants[0].variant_prices[i].value)
            }else if(product.variants[0].variant_prices[i].price_list.code.equals("BANBUON")){
                binding.tvWholesalePrice.text = GlobalFuntion.removeDecimal(product.variants[0].variant_prices[i].value)

            }
        }
    }
    fun showWarehouse(){
        var countInventory : Float = 0f
        var countOnHand : Float = 0f
        for(i in 0 until product.variants[0].inventories.size){
            countOnHand += product.variants[0].inventories[i].on_hand
            countInventory+= product.variants[0].inventories[i].available
        }

        binding.tvOnHand.text = GlobalFuntion.removeDecimal(countOnHand)
        binding.tvInventory.text = GlobalFuntion.removeDecimal(countInventory)
    }
    fun onClickInfoAdd(){
        binding.layoutRelativeAddInfo.setOnClickListener{
            if(binding.layoutLinearAddInfo.visibility== View.VISIBLE){
                binding.layoutLinearAddInfo.visibility = View.GONE
                binding.imgDownOrUp.setImageResource(R.drawable.ic_down)
            }else{
                binding.layoutLinearAddInfo.visibility = View.VISIBLE
                binding.imgDownOrUp.setImageResource(R.drawable.ic_up)
            }
        }
    }
    fun showInfoAdd(){
        if(product.category.isNullOrEmpty()){
            binding.tvProductType.text = "---"
        }else{
            binding.tvProductType.text = product.category
        }

        if(product.brand.isNullOrEmpty()){
            binding.tvBrand.text = "---"
        }else{
            binding.tvBrand.text = product.category
        }
        if(product.description.isNullOrEmpty()){
            binding.tvDescription.text = "---"
        }else{
            val htmlTagRegex = "<[^>]+>".toRegex()
            val result = product.description.replace(htmlTagRegex, "")
            binding.tvDescription.text = result
        }
        if(product.variants[0].sellable){
            binding.imgSellable.setImageResource(R.drawable.ic_check)
            val color = ContextCompat.getColor(this, R.color.green)
            binding.imgSellable.setColorFilter(color)
            binding.tvSellable.text="Cho phép bán"

        }else{
            binding.imgSellable.setImageResource(R.drawable.ic_error)
            binding.tvSellable.text="Không cho phép bán"
        }
        onClickInfoAdd()
    }
    fun showStatus(){
        if(product.status.equals("active")){
            val color = ContextCompat.getColor(this, R.color.green)
            binding.imgStatus.setColorFilter(color)
            binding.tvStatus.text = "Đang giao dịch"
        }else if(product.status.equals("inactive")){
            val color = ContextCompat.getColor(this, R.color.orange)
            binding.imgStatus.setColorFilter(color)
            binding.tvStatus.text = "Ngừng giao dịch"
        }
    }
    fun displayListVariantProduct(){
        var linearLayoutManager = LinearLayoutManager(this)
        binding.rcvVariant.layoutManager = linearLayoutManager
        var dividerItemDecoration = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        binding.rcvVariant.addItemDecoration(dividerItemDecoration)
        detailProductAdapter = DetailProductAdapter(product.variants,this)
        binding.rcvVariant.adapter = detailProductAdapter
    }
    fun displayListImageProduct(){
        var linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.rcvImage.layoutManager = linearLayoutManager
        imageAdapter = ImageAdapter(product.images, this)
        binding.rcvImage.adapter = imageAdapter
    }
    fun initToolBar(){
        binding.header.imgLeft.visibility=View.VISIBLE
        binding.header.imgLeft.setOnClickListener{
            finish()
        }
        binding.header.tvTitle.text="Chi tiết sản phẩm"
    }
    fun show(){
        if(product.images.isNullOrEmpty()){
            binding.rcvImage.visibility = View.VISIBLE
        }else{
            displayListImageProduct()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mDetailProductPresenter.disposable?.dispose()
    }
}