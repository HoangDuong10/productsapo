package com.example.product.ui.listproduct

import com.example.product.model.*
import com.example.product.ui.model.MetaData
import com.example.product.ui.model.Product
import com.example.product.ui.model.Variant

interface ListProductContracts {
    fun callApiErreor()
    fun callListProduct(mListProduct: MutableList<Product>, matadata: MetaData)
    fun callListVariant(mListVariant:  MutableList<Variant>, metaData: MetaData)

}