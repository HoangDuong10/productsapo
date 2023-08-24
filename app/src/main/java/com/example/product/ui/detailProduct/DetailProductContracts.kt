package com.example.product.ui.detailProduct

import com.example.product.ui.model.Product
import com.example.product.model.ProductDTO
interface DetailProductContracts {
    fun callApiErreor()
    fun callProduct(mProduct: Product)
}