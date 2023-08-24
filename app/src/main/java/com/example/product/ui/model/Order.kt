package com.example.product.ui.model

class Order {
    var sourceId:Int?=null
    var status:String?=null
    var orderLineItems: MutableList<OrderLineItem> = mutableListOf()

    constructor(sourceId: Int?, status: String?, orderLineItems: MutableList<OrderLineItem>) {
        this.sourceId = sourceId
        this.status = status
        this.orderLineItems = orderLineItems
    }
}