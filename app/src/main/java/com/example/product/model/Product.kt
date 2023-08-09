package com.example.product.model

class Product {
    var  name: String=""
    var variants : MutableList<Variants> = mutableListOf()
    var images : MutableList<Image> = mutableListOf()
    var id : Int = 0
    var category : String =""
    var brand : String =""
    var description : String =""
    var status : String = ""
    constructor()

    constructor( name: String, variants : MutableList<Variants>,image : MutableList<Image>){
        this.name=name
        this.variants =variants
        this.images = image

    }

}