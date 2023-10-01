package com.example.product.api.response

import com.example.product.api.dto.OrderSourceDTO
import com.google.gson.annotations.SerializedName

class OrderSourceResponse {
    @SerializedName("order_sources")
    var orderSources:MutableList<OrderSourceDTO>?=null
}