package com.example.product.ui.model

class OrderLineItem : java.io.Serializable {
    var variant=Variant()
    var quantity:Double=0.0

    fun getTotalAmountOneOrder():Double{
        return variant?.getRetailPrice()!! * quantity!!
    }
    fun getTotalTaxOneOrder():Double{
        var totalTax=0.0
        if(variant.outputVatRate!=null){
            totalTax=getTotalAmountOneOrder()* variant.outputVatRate!! /100
        }
        return totalTax
    }
    fun getTotalMoney():Double{
        var totalMoney=0.0
        totalMoney = if(variant.taxIncluded){
            getTotalTaxOneOrder()+getTotalAmountOneOrder()
        }else{
            getTotalAmountOneOrder()
        }
        return totalMoney
    }
}