package com.example.product.ui.model

import com.example.product.ui.AppConfig
import com.example.product.utils.NumberUtil.formatNoTrailingZero

class Product {
    var  name: String?=null
    var variants : MutableList<Variant> = mutableListOf()
    var images : MutableList<Image> = mutableListOf()
    var id : Int?=null
    var category : String?=null
    var brand : String?=null
    var description : String?=null
    var status : String?=null
    constructor()
    fun isStatus():Boolean{
        return status=="active"
    }
    fun getStringCategory():String{
        if(category.isNullOrEmpty()){
            return AppConfig.InValidText
        }
        return category!!
    }
    fun getStringBrand():String{
        if(brand.isNullOrEmpty()){
            return AppConfig.InValidText
        }
        return brand!!
    }
    fun getStringDescription():String{
        if(description.isNullOrEmpty()){
            return AppConfig.InValidText
        }
        val result = description!!.replace("<[^>]+>".toRegex(), "")
        return result
    }
    fun getTotalOnhand(): Double {
        var count=0.0
        count+=variants.sumOf { it -> it.inventories.sumOf { it.onHand!! } }
        return count
    }

}