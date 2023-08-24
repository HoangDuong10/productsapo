package com.example.product.model

import com.google.gson.annotations.SerializedName

class InventorieDTO{

    var available: Double?=null
    @SerializedName("on_hand")
    var onHand: Double?=null
}