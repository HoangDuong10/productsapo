package com.example.product.ui.model

import java.io.Serializable

class Inventorie: Serializable {
    var available: Double?=null
    var onHand: Double?=null

    constructor(available: Double?, onHand: Double?) {
        this.available = available
        this.onHand = onHand
    }
}