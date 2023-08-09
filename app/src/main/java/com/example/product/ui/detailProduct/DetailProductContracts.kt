package com.example.product.ui.detailProduct

import com.example.product.model.Product
interface DetailProductContracts {
    fun callApiErreor()
    fun setData(mProduct: Product)
}