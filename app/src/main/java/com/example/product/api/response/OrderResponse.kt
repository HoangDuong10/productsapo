package com.example.product.api.response

import com.example.product.api.dto.OrderDTO

class OrderResponse {
    var order : OrderDTO?=null

    constructor(order: OrderDTO?) {
        this.order = order
    }
}