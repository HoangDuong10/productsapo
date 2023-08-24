package com.example.product.ui.createorder

import com.example.product.api.dto.OrderSource

interface CreateOrderContracts {
    fun callApiError()
    fun callAPiSuccuss()
    fun callListSourceId(orderSource: MutableList<OrderSource>)
}