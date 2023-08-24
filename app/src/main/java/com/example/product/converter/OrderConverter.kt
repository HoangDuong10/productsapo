package com.example.product.converter

import com.example.product.api.dto.OrderDTO
import com.example.product.api.dto.OrderLineItemDTO
import com.example.product.ui.model.Order

object OrderConverter {
    fun toDTO(model: Order): OrderDTO {
        val dto= OrderDTO()
        dto.status=model.status
        dto.sourceId=model.sourceId
        dto.orderLineItems= OrderLineItemConverter.toListDTO(model.orderLineItems) as MutableList<OrderLineItemDTO>
        return dto
    }
    fun toListDTO(listModel:List<Order>):List<OrderDTO>{
        return listModel.map { toDTO(it) }
    }
}