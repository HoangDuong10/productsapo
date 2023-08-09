package com.example.product.model

import java.io.Serializable


class VariantsPrice(var value : Float,var price_list_id : Int,var name : String,var price_list: PriceList):
    Serializable {

}