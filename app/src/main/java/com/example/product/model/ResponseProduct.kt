package com.example.product.model

data class ResponseProduct(var products: MutableList<Product>,var product: Product,var metadata: MetaData) {
}