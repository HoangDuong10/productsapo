package com.example.product.ui.detailVariant

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.product.R
import com.example.product.adapter.DetailProductAdapter
import com.example.product.adapter.DetailVariantAdapter
import com.example.product.databinding.ActivityDetailProductOrVariantBinding
import com.example.product.model.Product
import com.example.product.model.Variants
import com.example.product.ui.detailProduct.DetailProductPresenter
import com.example.product.utils.GlobalFuntion

class DetailVariantActivity : AppCompatActivity(),DetailVariantContracts{
    private  var productId : Int =0
    private var variantId : Int = 0
    private lateinit var product: Product
    private lateinit var listVariant : MutableList<Variants>
    private lateinit var adapter: DetailVariantAdapter
    private lateinit var variant:Variants
    private lateinit var mDetailVariantPresenter: DetailVariantPresenter
    private lateinit var binding : ActivityDetailProductOrVariantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductOrVariantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        variant = Variants()
        listVariant = mutableListOf()
        product = Product()
        productId = intent.getIntExtra("product_id",0)
        variantId = intent.getIntExtra("variant_id",0)
        mDetailVariantPresenter = DetailVariantPresenter(this)
        mDetailVariantPresenter.getDetailVariant(productId,variantId)
        initToolBar()
    }

    override fun callApiErreor() {
        GlobalFuntion.showToastMessage(this,"Call api không thành công")
    }

    override fun callDetailVariant(mVariant: Variants) {
        variant = mVariant
        mDetailVariantPresenter.getDetailProduct(productId)

    }
    fun showData(){
        getListDetailVariant()
        if(listVariant.isNullOrEmpty()){
            binding.cardViewNameProduct.visibility = View.GONE
            binding.layoutLinearHeaderAddInfo.visibility = View.GONE
            binding.cardViewStatus.visibility = View.GONE
            binding.cardViewVariantProduct.visibility = View.GONE
            showInfo()
            showTaxPrice()
            showWarehouse()
            showCanSell()
            displayImageDetailVariant()
        }else{
            binding.layoutListVariant.visibility=View.VISIBLE
            binding.layoutLinearHeaderAddInfo.visibility = View.GONE
            binding.cardViewVariantProduct.visibility=View.VISIBLE
            binding.layoutLinearAddInfo.visibility = View.GONE
            binding.cardViewStatus.visibility = View.GONE
            binding.cardViewNameProduct.visibility= View.GONE
            showInfo()
            showTaxPrice()
            showWarehouse()
            showCanSell()
            displayListVariantProduct()
            displayImageDetailVariant()
        }
    }
    override fun callDetailProduct(mProduct: Product) {
        product = mProduct
        showData()
    }
    fun getListDetailVariant() : MutableList<Variants>{
        for(i in 0 until product.variants.size){
            if(variant.id == product.variants[i].packsize_root_id){
                listVariant.add(product.variants[i])
            }
        }
        return listVariant
    }


    fun showInfo(){
        binding.tvName.text = variant.name
        binding.tvSku.text = variant.sku
        binding.tvBarCode.text = variant.barcode
        binding.tvWeight.text = GlobalFuntion.removeDecimal(variant.weight_value)+variant.weight_unit
        if(variant.unit.isNullOrEmpty()){
            binding.tvUnit.text="---"
        }else{
            binding.tvUnit.text = variant.unit
        }
        if(!variant.opt1.isNullOrEmpty()){
            binding.layoutOpt1.visibility = View.VISIBLE
            binding.tvOpt1.text=variant.opt1
        }
        if(variant.packsize_quantity!=null &&variant.packsize_quantity!=0f){
           binding.layoutUnitQuantity.visibility= View.VISIBLE
            binding.tvQuantity.text = GlobalFuntion.removeDecimal(variant.packsize_quantity)
        }
        if(!variant.opt2.isNullOrEmpty()){
            binding.layoutOpt2.visibility = View.VISIBLE
            binding.tvOpt2.text=variant.opt2
        }
        if(!variant.opt3.isNullOrEmpty()){
            binding.layoutOpt3.visibility = View.VISIBLE
            binding.tvOpt3.text=variant.opt3
        }
    }
    fun showTaxPrice(){
        if(variant.taxable){
            binding.layoutLinearTax.visibility = View.VISIBLE
            binding.tvInputTax.text = GlobalFuntion.removeDecimal(variant.input_vat_rate)
            binding.tvOutputTax.text = GlobalFuntion.removeDecimal(variant.output_vat_rate)
        }else{
            binding.layoutLinearTax.visibility = View.GONE
        }
        for(i in 0 until variant.variant_prices.size){
            if(variant.variant_prices[i].price_list.code.equals("BANLE")){
                binding.tvRetailPrice.text = GlobalFuntion.removeDecimal(variant.variant_prices[i].value)
            }else if (variant.variant_prices[i].price_list.code.equals("GIANHAP")){
                binding.tvImportPrice.text = GlobalFuntion.removeDecimal(variant.variant_prices[i].value)
            }else if(variant.variant_prices[i].price_list.code.equals("BANBUON")){
                binding.tvWholesalePrice.text = GlobalFuntion.removeDecimal(variant.variant_prices[i].value)

            }
        }
    }
    fun showWarehouse(){
        var countAvailable : Float = 0f
        var countOnHand : Float = 0f
        for(i in 0 until variant.inventories.size){
            countOnHand += variant.inventories[i].on_hand
            countAvailable+= variant.inventories[i].available
        }

        binding.tvOnHand.text = GlobalFuntion.removeDecimal(countOnHand)
        binding.tvInventory.text = GlobalFuntion.removeDecimal(countAvailable)
    }
    fun showCanSell(){
        if(variant.sellable){
            binding.imgSellable.setImageResource(R.drawable.ic_check)
            val color = ContextCompat.getColor(this, R.color.green)
            binding.imgSellable.setColorFilter(color)
            binding.tvSellable.text="Cho phép bán"

        }else{
            binding.imgSellable.setImageResource(R.drawable.ic_error)
            binding.tvSellable.text="Không cho phép bán"
        }
    }
    fun displayListVariantProduct(){
        var linearLayoutManager = LinearLayoutManager(this)
        binding.rcvVariant.layoutManager = linearLayoutManager
        var dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rcvVariant.addItemDecoration(dividerItemDecoration)
        adapter = DetailVariantAdapter(listVariant ,this)
        binding.rcvVariant.adapter = adapter
    }
    fun displayImageDetailVariant(){
        if(!variant.images.isNullOrEmpty()){
            binding.layoutImage.visibility = View.GONE
            binding.imgDetailVariant.visibility=View.VISIBLE
            Glide.with(this).load(variant.images[0].fullPath).into(binding.imgDetailVariant)
        }else{
            binding.imgDetailVariant.visibility=View.GONE
        }

    }
    fun initToolBar(){
        binding.header.imgLeft.visibility=View.VISIBLE
        binding.header.imgLeft.setOnClickListener{
            finish()
        }
        binding.header.tvTitle.text="Chi tiết phiên bản"
    }
    override fun onDestroy() {
        super.onDestroy()
        mDetailVariantPresenter.variantDisposable?.dispose()
        mDetailVariantPresenter.productDisposable?.dispose()
    }
}