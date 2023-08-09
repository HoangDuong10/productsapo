package com.example.product.ui.listproduct

import com.example.product.model.MetaData
import com.example.product.model.Product
import com.example.product.model.Variants

interface ListProductContracts {
    fun callApiErreor()
    fun setListProduct(mListProduct: MutableList<Product>,matadata:MetaData)
    fun setListVariant(mListVariant:  MutableList<Variants>, metaData: MetaData)

}