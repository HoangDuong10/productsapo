package com.example.product.ui.selectvariant

import com.example.product.ui.model.MetaData
import com.example.product.ui.model.OrderLineItem

interface SelectVariantContracts {
    fun callApiErreor(message:String)
    fun setListVariant(listOrderLineItem:   MutableList<OrderLineItem>, metaData: MetaData)

}