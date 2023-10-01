package com.example.product.ui.detailProduct

import com.example.product.ui.model.Product
import com.example.product.model.ProductDTO
interface DetailProductContracts {
    fun callApiErreor(message:String)
    fun callProduct(mProduct: Product)
}