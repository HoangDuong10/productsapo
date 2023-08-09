package com.example.product.ui.detailVariant

import com.example.product.model.Product
import com.example.product.model.Variants

interface DetailVariantContracts {
    fun callApiErreor()
    fun callDetailVariant(mVariant: Variants)
    fun callDetailProduct(mProduct: Product)
}