package com.example.product.ui.model

import java.io.Serializable

class PriceList : Serializable {
    var code: String?=null
    constructor()
    fun isRetailPrice():Boolean {
        return code == "BANLE"
    }
    fun isWholesalePrice():Boolean{
        return code=="BANBUON"
    }
    fun isImportPrice():Boolean{
        return code=="GIANHAP"
    }
}