package com.example.product.presenter.converter

import com.example.product.model.*
import com.example.product.ui.model.*

object VariantConverter {
    fun variantDTOToVariant(dto: VariantDTO): Variant {
        val variant = Variant()
        variant.name = dto.name
        variant.inventories = InventorieConverter.listInventorieDTOToListInventorie(dto.inventories) as MutableList<Inventorie>
        variant.variantPrices = VariantPriceConverter.listVariantPriceDTOToListVariantPrice(dto.variantPrices) as MutableList<VariantsPrice>
        variant.images.addAll(dto.images?.let { ImageConverter.listImageToListImageDTO(it) }?: mutableListOf())
        variant.description = dto.description
        variant.imageId=dto.imageId
        variant.opt1 = dto.opt1
        variant.opt2 = dto.opt2
        variant.opt3 = dto.opt3
        variant.sellable = dto.sellable
        variant.sku = dto.sku
        variant.barcode = dto.barcode
        variant.weightValue = dto.weightValue
        variant.weightUnit = dto.weightUnit
        variant.unit = dto.unit
        variant.inputVatRate = dto.inputVatRate
        variant.outputVatRate = dto.outputVatRate
        variant.productName = dto.productName
        variant.packsize = dto.packsize
        variant.id = dto.id
        variant.productId = dto.productId
        variant.packsizeRootId = dto.packsizeRootId
        variant.taxable = dto.taxable
        variant.packSizeQuantity = dto.packsizeQuantity
        variant.taxIncluded = dto.taxIncluded
        return variant
    }

    fun listVariantDTOtoListVariant(initial: List<VariantDTO>): List<Variant> {
        return initial.map { variantDTOToVariant(it) }
    }
//    fun OrderLineItemToOrderLineItemDTO(variant: Variant): OrderLineItem {
//        val orderLineItem= OrderLineItem()
//        orderLineItem.variantId=variant.id
//        orderLineItem.productId=variant.productId
//        orderLineItem.price=variant.getRetailPrice()
//        orderLineItem.quantity=variant.total
//        return orderLineItem
//    }
//    fun listOrderLineItemToListOrderLineItemDTO(initial: List<Variant>): List<OrderLineItem> {
//        return initial.map { OrderLineItemToOrderLineItemDTO(it) }
//    }
    fun variantDTOToOrderLineItem(dto:VariantDTO) :OrderLineItem{
        val orderLineItem = OrderLineItem()
        orderLineItem.variant= variantDTOToVariant(dto)
        return orderLineItem
    }

    fun listVariantDTOtoListOrderLineItem(initial: List<VariantDTO>): List<OrderLineItem> {
        return initial.map { variantDTOToOrderLineItem(it) }
    }
}
