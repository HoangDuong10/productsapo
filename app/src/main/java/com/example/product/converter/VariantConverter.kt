package com.example.product.converter

import com.example.product.model.*
import com.example.product.ui.model.*

object VariantConverter {
    fun toModel(dto: VariantDTO): Variant {
        val variant = Variant()
        variant.name = dto.name
        variant.inventories = InventorieConverter.toModelList(dto.inventories) as MutableList<Inventorie>
        variant.variantPrices = VariantPriceConverter.toModelList(dto.variantPrices) as MutableList<VariantsPrice>
        dto.images?.let { nonNullValue ->
            variant.images = dto.images?.let { ImageConverter.toModelList(it) } as MutableList<Image>
        }
        variant.description = dto.description
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

    fun toModelList(initial: List<VariantDTO>): List<Variant> {
        return initial.map { toModel(it) }
    }
    fun toModelOrderLineItem(variant: Variant): OrderLineItem {
        val orderLineItem= OrderLineItem()
        orderLineItem.variantId=variant.id
        orderLineItem.productId=variant.productId
        orderLineItem.price=variant.getRetailPrice()
        orderLineItem.quantity=variant.total
        return orderLineItem
    }
    fun toModelListOrderLineItem(initial: List<Variant>): List<OrderLineItem> {
        return initial.map { toModelOrderLineItem(it) }
    }
}
