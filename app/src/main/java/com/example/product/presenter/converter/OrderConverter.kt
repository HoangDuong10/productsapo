package com.example.product.presenter.converter

import com.example.product.api.dto.OrderDTO
import com.example.product.api.dto.OrderLineItemDTO
import com.example.product.ui.model.Order

object OrderConverter {
    fun orderToOrderDTO(model: Order): OrderDTO {
        val dto= OrderDTO()
        dto.status=model.status
        dto.sourceId=model.sourceId
        dto.orderLineItems= OrderLineItemConverter.listOrderLineItemToOrderLineItemDTO(model.listOrderLineItems) as MutableList<OrderLineItemDTO>
        return dto
    }
    fun listOrderToListOrderDTO(listModel:List<Order>):List<OrderDTO>{
        return listModel.map { orderToOrderDTO(it) }
    }
}