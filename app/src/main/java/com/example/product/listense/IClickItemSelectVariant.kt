package com.example.product.listense

import com.example.product.model.Variants

interface IClickItemSelectVariant {
    fun onClickItemSelectVariant(variants: Variants,position:Int )

}