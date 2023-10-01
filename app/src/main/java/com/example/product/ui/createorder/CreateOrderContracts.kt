package com.example.product.ui.createorder

import com.example.product.ui.model.OrderSource

interface CreateOrderContracts {
    fun callApiError(message:String)
    fun callAPiSuccuss()
    fun callListSourceId(orderSource: MutableList<OrderSource>)

}