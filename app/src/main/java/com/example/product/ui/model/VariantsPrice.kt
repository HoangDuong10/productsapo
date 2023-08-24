package com.example.product.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class VariantsPrice: Serializable {
    var value : Double?=null
    var priceListId : Int?=null
    var name : String?=null
    var priceList: PriceList?=null

    constructor()
}