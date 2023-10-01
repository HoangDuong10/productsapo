package com.example.product.presenter.converter

import com.example.product.ui.model.Inventorie
import com.example.product.model.InventorieDTO

object InventorieConverter {
    fun inventorieDTOToInventorie(dto:InventorieDTO) : Inventorie {
        return Inventorie(dto.available,dto.onHand)
    }
    fun listInventorieDTOToListInventorie(initial: List<InventorieDTO>): List<Inventorie> {
        return initial.map { inventorieDTOToInventorie(it) }
    }
}