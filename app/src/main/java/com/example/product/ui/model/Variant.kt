package com.example.product.ui.model

import com.example.product.ui.AppConfig
import com.example.product.utils.NumberUtil.formatNumber
import java.io.Serializable

class Variant() : Serializable {
    var inventories: MutableList<Inventorie> = mutableListOf()
    var name: String?=null
    var variantPrices : MutableList<VariantsPrice> = mutableListOf()
    var images : MutableList<Image> = mutableListOf()
    var description : String?=null
    var imageId :Int?=null
    var opt1 : String?=null
    var opt2 : String?=null
    var opt3 : String?=null
    var sellable : Boolean = false
    var sku : String?=null
    var barcode : String?=null
    var weightValue : Double?=null
    var weightUnit : String?=null
    var unit : String?=null
    var inputVatRate : Double?=null
    var outputVatRate : Double?=null
    var productName : String?=null
    var packsize : Boolean =false
    var id : Int?=null
    var productId : Int?=null
    var packsizeRootId : Int?=null
    var taxable : Boolean= true
    var packSizeQuantity : Double?=null
    var taxIncluded:Boolean=false

    fun getTotalOnhand(): Double {
        return inventories.sumOf { it.onHand ?:0.0 }
    }
    fun getTotalAvailable(): Double {
        return inventories.sumOf { it.available?:0.0 }
    }
    fun isOtp():Boolean{
        return opt1=="Mặc định"
    }
    fun getRetailPrice():Double {
        var retailPrice = 0.0
        for (item in variantPrices) {
            if (item.priceList?.isRetailPrice()!!) {
                retailPrice=item.value!!
            }
        }
        return retailPrice
    }
    fun getImportPrice():Double {
        var importPrice = 0.0
        for (item in variantPrices) {
            if (item.priceList?.isImportPrice()!!) {
                importPrice=item.value!!
            }
        }
        return importPrice
    }
    fun getWholesalePrice():Double {
        var wholesalePrice = 0.0
        for (item in variantPrices) {
            if (item.priceList?.isWholesalePrice()!!) {
                wholesalePrice=item.value!!
            }
        }
        return wholesalePrice
    }
    fun getInputVatRate():String{
        return inputVatRate?.formatNumber()+ AppConfig.Percent
    }
    fun getOutputVatRate():String{
        return outputVatRate?.formatNumber()+AppConfig.Percent
    }
    fun getStringUnit():String{
        if(unit.isNullOrEmpty()){
            return AppConfig.InValidText
        }
        return unit!!
    }
    fun getFullPath(): String? {
        val image = images.firstOrNull { it.id == imageId }
        return image?.fullPath
    }

//    fun getTotalAmountOneOrder():Double{
//        return getRetailPrice()*total!!
//    }
//    fun getTotalTaxOneOrder():Double{
//        var totalTax=0.0
//        if(outputVatRate!=null){
//            totalTax=getTotalAmountOneOrder()* outputVatRate!! /100!!
//        }
//        return totalTax
//    }
//    fun getTotalMoney():Double{
//        var totalMoney=0.0
//        totalMoney = if(!taxIncluded){
//            getTotalTaxOneOrder()+getTotalAmountOneOrder()
//        }else{
//            getTotalAmountOneOrder()
//        }
//        return totalMoney
//    }

}

