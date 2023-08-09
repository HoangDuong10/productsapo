package com.example.product.model

import java.io.Serializable

class Variants : Serializable {
    var inventories: MutableList<Inventories> = mutableListOf()
    var name: String = ""
    var variant_prices : MutableList<VariantsPrice> = mutableListOf()
    var images : MutableList<Image> = mutableListOf()
    var description : String = ""
    var opt1 : String =""
    var opt2 : String =""
    var opt3 : String =""
    var sellable : Boolean = false
    var sku : String =""
    var barcode : String = ""
    var weight_value : Float = 0f
    var weight_unit : String = ""
    var unit : String = ""
    var input_vat_rate : Float = 0f
    var output_vat_rate : Float = 0f
    var product_name : String =""
    var packsize : Boolean =false
    var id : Int = 0
    var product_id : Int = 0
    var packsize_root_id : Int =0
    var taxable : Boolean= true
    var packsize_quantity : Float = 0f
    var total :Int?=null
    var tax_included:Boolean=false
    constructor()

}

