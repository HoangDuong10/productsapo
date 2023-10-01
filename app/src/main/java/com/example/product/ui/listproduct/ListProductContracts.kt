package com.example.product.ui.listproduct

import com.example.product.ui.model.MetaData
import com.example.product.ui.model.Product
import com.example.product.ui.model.Variant

interface ListProductContracts {
    fun callApiError(message:String)
    fun callListProduct(listProduct: MutableList<Product>, metaData: MetaData)
    fun callListVariant(listVariant:  MutableList<Variant>, metaData: MetaData)

}