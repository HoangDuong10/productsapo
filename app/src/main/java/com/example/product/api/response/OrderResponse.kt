package com.example.product.api.response

import com.example.product.api.dto.OrderDTO

class OrderResponse {
    constructor(order: OrderDTO?) {
        this.order = order
    }

    var order : OrderDTO?=null
}