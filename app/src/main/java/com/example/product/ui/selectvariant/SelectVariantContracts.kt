package com.example.product.ui.selectvariant

import com.example.product.model.MetaData
import com.example.product.model.Product
import com.example.product.model.Variants

interface SelectVariantContracts {
    fun callApiErreor()
    fun setListVariant(mListVariant:  MutableList<Variants>, metaData: MetaData)

}