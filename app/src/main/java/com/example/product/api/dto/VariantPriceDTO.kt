package com.example.product.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class VariantPriceDTO{
    var value : Double?=null

    @SerializedName("price_list_id")
    var priceListId : Int?=null

    var name : String?=null

    @SerializedName("price_list")
    var priceList: PriceListDTO?=null
}