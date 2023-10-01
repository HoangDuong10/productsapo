package com.example.product.presenter.converter
import com.example.product.ui.model.PriceList
import com.example.product.model.PriceListDTO

object PriceListConverter {
    fun priceListDTOToPriceList(dto: PriceListDTO): PriceList {
        val priceList= PriceList()
        priceList.code = dto.code
        return priceList
    }
}