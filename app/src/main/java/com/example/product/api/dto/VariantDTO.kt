package com.example.product.model

import com.google.gson.annotations.SerializedName


class VariantDTO {
    var inventories: MutableList<InventorieDTO> = mutableListOf()
    var name: String?=null

    @SerializedName("variant_prices")
    var variantPrices : MutableList<VariantPriceDTO> = mutableListOf()

    var images : MutableList<ImageDTO>?=null
    var description : String?=null
    var opt1 : String?=null
    var opt2 : String?=null
    var opt3 : String?=null
    var sellable : Boolean = false
    var sku : String?=null
    var barcode : String?=null

    @SerializedName("weight_value")
    var weightValue : Double?=null

    @SerializedName("weight_unit")
    var weightUnit : String?=null
    var unit : String?=null

    @SerializedName("input_vat_rate")
    var inputVatRate : Double?=null

    @SerializedName("output_vat_rate")
    var outputVatRate : Double?=null

    @SerializedName("product_name")
    var productName : String?=null

    var packsize : Boolean =false
    var id : Int?=null

    @SerializedName("product_id")
    var productId : Int?=null

    @SerializedName("packsize_root_id")
    var packsizeRootId : Int?=null

    var taxable : Boolean= true

    @SerializedName("packsize_quantity")
    var packsizeQuantity : Double?=null

    @SerializedName("tax_included")
    var taxIncluded:Boolean=false


}

