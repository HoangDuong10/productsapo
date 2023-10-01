package com.example.product.presenter.converter

import com.example.product.ui.model.MetaData
import com.example.product.model.MetaDataDTO

object MetaDataConverter {
    fun metaDataDTOToMetaData(dto:MetaDataDTO): MetaData {
        val metaData = MetaData()
        metaData.total = dto.total
        metaData.page = dto.page
        metaData.limit=dto.limit
        return metaData
    }
}