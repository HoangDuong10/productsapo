package com.example.product.converter
import com.example.product.ui.model.PriceList
import com.example.product.model.PriceListDTO
import org.modelmapper.ModelMapper

object PriceListConverter {
    val mapper= ModelMapper()
    fun toModel(dto: PriceListDTO): PriceList {
        val priceList= PriceList()
        priceList.code = dto.code
        return priceList
    }
}