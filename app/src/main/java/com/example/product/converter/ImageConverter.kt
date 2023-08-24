package com.example.product.converter

import com.example.product.ui.model.Image
import com.example.product.model.ImageDTO
import com.example.product.ui.model.Product
import com.example.product.model.ProductDTO
import org.modelmapper.ModelMapper

object ImageConverter {
    fun toModel(dto: ImageDTO): Image {
        val image = Image()
        image.fullPath=dto.fullPath
        return image
    }
    fun toModelList(initial: List<ImageDTO>): List<Image> {
        return initial.map { toModel(it) }
    }
}