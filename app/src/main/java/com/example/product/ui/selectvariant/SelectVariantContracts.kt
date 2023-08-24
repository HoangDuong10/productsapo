package com.example.product.ui.selectvariant

import com.example.product.ui.model.MetaData
import com.example.product.ui.model.Variant

interface SelectVariantContracts {
    fun callApiErreor()
    fun setListVariant(mListVariant:  MutableList<Variant>, metaData: MetaData)

}