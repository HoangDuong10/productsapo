package com.example.product.ui.detailVariant

import com.example.product.ui.model.Product
import com.example.product.model.ProductDTO
import com.example.product.ui.model.Variant
import com.example.product.model.VariantDTO

interface DetailVariantContracts {
    fun callApiErreor()
    fun callDetailVariant(mVariant: Variant)
    fun callDetailProduct(mProduct: Product)
}