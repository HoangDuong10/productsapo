package com.example.product.converter

import com.example.product.api.dto.OrderLineItemDTO
import com.example.product.ui.model.OrderLineItem

object OrderLineItemConverter {
    fun toModel(dto: OrderLineItemDTO): OrderLineItem {
        val orderLineItem= OrderLineItem()
        orderLineItem.price=dto.price
        orderLineItem.productId=dto.productId
        orderLineItem.variantId=dto.variantId
        orderLineItem.quantity=dto.quantity
        return orderLineItem
    }
    fun toDTO(model: OrderLineItem): OrderLineItemDTO {
        val orderLineItemDTO= OrderLineItemDTO()
        orderLineItemDTO.price=model.price
        orderLineItemDTO.productId=model.productId
        orderLineItemDTO.variantId=model.variantId
        orderLineItemDTO.quantity=model.quantity
        return orderLineItemDTO
    }
    fun toListModel(listDTO:List<OrderLineItemDTO>):List<OrderLineItem>{
        return listDTO.map { toModel(it) }
    }
    fun toListDTO(listModel:List<OrderLineItem>):List<OrderLineItemDTO>{
        return listModel.map { toDTO(it) }
    }
}
