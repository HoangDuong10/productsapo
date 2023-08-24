package com.example.product.converter

import com.example.product.ui.model.MetaData
import com.example.product.model.MetaDataDTO
import org.modelmapper.ModelMapper

object MetaDataConverter {
    val mapper= ModelMapper()
    fun toModel(dto:MetaDataDTO): MetaData {
        return mapper.map(dto, MetaData::class.java)
    }
}