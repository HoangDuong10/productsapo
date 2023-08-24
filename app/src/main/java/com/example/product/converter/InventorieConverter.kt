package com.example.product.converter

import com.example.product.ui.model.Inventorie
import com.example.product.model.InventorieDTO
import com.example.product.ui.model.Product
import com.example.product.model.ProductDTO

object InventorieConverter {
    fun toModel(dto:InventorieDTO) : Inventorie {
        return Inventorie(dto.available,dto.onHand)
    }
    fun toModelList(initial: List<InventorieDTO>): List<Inventorie> {
        return initial.map { toModel(it) }
    }
}