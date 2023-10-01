package com.example.product.presenter.converter

import com.example.product.model.VariantPriceDTO
import com.example.product.ui.model.VariantsPrice

object VariantPriceConverter {
     fun variantPriceDTOToVariantPrice(dto:VariantPriceDTO): VariantsPrice {
        val variantsPrice = VariantsPrice()
        variantsPrice.value=dto.value
        variantsPrice.priceListId=dto.priceListId
        variantsPrice.name=dto.name
        variantsPrice.priceList= dto.priceList?.let { PriceListConverter.priceListDTOToPriceList(it) }
        return variantsPrice
    }
    fun listVariantPriceDTOToListVariantPrice(initial: List<VariantPriceDTO>): List<VariantsPrice> {
        return initial.map { variantPriceDTOToVariantPrice(it) }
    }
}