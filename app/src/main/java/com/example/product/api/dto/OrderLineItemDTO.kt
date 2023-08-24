package com.example.product.api.dto

import com.google.gson.annotations.SerializedName

class OrderLineItemDTO {
    @SerializedName("product_id")
    var productId:Int?=null

    @SerializedName("variant_id")
    var variantId:Int?=null

    @SerializedName("price")
    var price:Double?=null

    @SerializedName("quantity")
    var quantity:Double?=null

}