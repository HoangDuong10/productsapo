package com.example.product.presenter.converter

import com.example.product.api.dto.OrderSourceDTO
import com.example.product.model.ProductDTO
import com.example.product.ui.model.OrderSource
import com.example.product.ui.model.Product

object OrderSourceConverter {
    fun orderSourceDTOToOrderSource(dto: OrderSourceDTO): OrderSource {
        val orderSource= OrderSource()
        orderSource.id = dto.id
        orderSource.name=dto.name
        return orderSource
    }
    fun listOrderSourceDTOToListOrderSource(initial: List<OrderSourceDTO>): List<OrderSource> {
        return initial.map { orderSourceDTOToOrderSource(it) }
    }
}