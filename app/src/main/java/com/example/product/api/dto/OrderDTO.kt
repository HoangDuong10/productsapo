package com.example.product.api.dto

import com.google.gson.annotations.SerializedName

class OrderDTO {
    @SerializedName("source_id")
    var sourceId:Int?=null

    @SerializedName("status")
    var status:String?=null

    @SerializedName("order_line_items")
    var orderLineItems: MutableList<OrderLineItemDTO> = mutableListOf()

}