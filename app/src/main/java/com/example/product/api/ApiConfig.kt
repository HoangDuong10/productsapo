package com.example.product.api

object ApiConfig {
    const val SESSION_ID_VALUE ="0a7d5c7ea94f78da201c379786eaa091"
    const val SESSION_ID = "X-Sapo-SessionId"
    const val BASE_URL="https://mobile-test.mysapogo.com/"
    const val PRODUCT_LIST_URL = "admin/products.json"
    const val VARIANT_LIST_URL = "admin/variants/search.json"
    const val PRODUCT_URL="admin/products/{id}.json"
    const val VARIANT_URL = "admin/products/{product_id}/variants/{variant_id}.json"
    const val ORDER_URL = "admin/orders.json"
    const val ORDER_SOURCE_URL = "admin/order_sources.json"
}