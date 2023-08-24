package com.example.product.converter

import com.example.product.ui.model.Variant
import com.example.product.model.VariantDTO
import com.example.product.model.VariantPriceDTO
import com.example.product.ui.model.VariantsPrice
import org.modelmapper.ModelMapper

object VariantPriceConverter {
    val mapper= ModelMapper()
    fun toModel(dto:VariantPriceDTO): VariantsPrice {
        val variantsPrice = VariantsPrice()
        variantsPrice.value=dto.value
        variantsPrice.priceListId=dto.priceListId
        variantsPrice.name=dto.name
        variantsPrice.priceList= dto.priceList?.let { PriceListConverter.toModel(it) }
        return variantsPrice
    }
    fun toModelList(initial: List<VariantPriceDTO>): List<VariantsPrice> {
        return initial.map {toModel(it) }
    }
}