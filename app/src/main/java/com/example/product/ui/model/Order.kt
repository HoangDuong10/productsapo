package com.example.product.ui.model

class Order {
    var sourceId:Int?=null
    var status:String?=null
    var listOrderLineItems: MutableList<OrderLineItem> = mutableListOf()

    constructor(sourceId: Int?, status: String?, orderLineItems: MutableList<OrderLineItem>) {
        this.sourceId = sourceId
        this.status = status
        this.listOrderLineItems = orderLineItems
    }
    constructor()
     fun getTotalTaxOrder() : Double{
        return listOrderLineItems.sumOf { it.getTotalTaxOneOrder() }
     }

    fun getTotalQuantity():Double{
        return listOrderLineItems.sumOf { it.quantity }
    }
    fun getTotalAmountOrder():Double{
        return listOrderLineItems.sumOf { it.getTotalAmountOneOrder() }
    }

    fun getTotalMoney():Double{
        return listOrderLineItems.sumOf { it.getTotalMoney() }
    }

}