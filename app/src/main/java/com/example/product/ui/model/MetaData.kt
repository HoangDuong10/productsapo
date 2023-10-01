package com.example.product.ui.model

class MetaData() {
    var total:Int=0
    var page:Int=0
    var limit:Int=0

    fun isLoadMore() : Boolean{
        return total>page*limit
    }
}