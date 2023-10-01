package com.example.product.presenter.converter

import com.example.product.api.dto.OrderLineItemDTO
import com.example.product.ui.model.OrderLineItem

object OrderLineItemConverter {
    fun orderLineItemDTOToOrderLineItem(dto: OrderLineItemDTO): OrderLineItem {
        val orderLineItem= OrderLineItem()
//        orderLineItem.price=dto.price
//        orderLineItem.productId=dto.productId
//        orderLineItem.variantId=dto.variantId
//        orderLineItem.quantity=dto.quantity
        return orderLineItem
    }
    fun orderLineItemToOrderLineItemDTO(model: OrderLineItem): OrderLineItemDTO {
        val orderLineItemDTO= OrderLineItemDTO()
        orderLineItemDTO.price=model.getTotalAmountOneOrder()
        orderLineItemDTO.productId=model.variant.productId
        orderLineItemDTO.variantId=model.variant.id
        orderLineItemDTO.quantity=model.quantity
        return orderLineItemDTO
    }
    fun listOrderLineItemDTOOrderLineItem(listDTO:List<OrderLineItemDTO>):List<OrderLineItem>{
        return listDTO.map { orderLineItemDTOToOrderLineItem(it) }
    }
    fun listOrderLineItemToOrderLineItemDTO(listModel:List<OrderLineItem>):List<OrderLineItemDTO>{
        return listModel.map { orderLineItemToOrderLineItemDTO(it) }
    }
}
