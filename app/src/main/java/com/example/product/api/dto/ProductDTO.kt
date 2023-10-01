package com.example.product.model

class ProductDTO {
    var name: String?=null
    var variants : MutableList<VariantDTO> = mutableListOf()
    var images : MutableList<ImageDTO>? = null
    var id : Int?=null
    var category : String?=null
    var brand : String?=null
    var description : String?=null
    var status : String?=null
}