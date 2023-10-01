package com.example.product.ui.detailVariant

import com.example.product.ui.model.Product
import com.example.product.ui.model.Variant

interface DetailVariantContracts {
    fun callApiError(message:String)
    fun callDetailVariant(mVariant: Variant)
    fun callDetailProduct(mProduct: Product)
}