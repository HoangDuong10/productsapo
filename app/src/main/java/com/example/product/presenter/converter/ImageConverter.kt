package com.example.product.presenter.converter

import com.example.product.ui.model.Image
import com.example.product.model.ImageDTO

object ImageConverter {
    fun imageToImageDTO(dto: ImageDTO): Image {
        val image = Image()
        image.fullPath=dto.fullPath
        image.id=dto.id
        return image
    }
    fun listImageToListImageDTO(initial: List<ImageDTO>): List<Image> {
        return initial.map { imageToImageDTO(it) }
    }
}