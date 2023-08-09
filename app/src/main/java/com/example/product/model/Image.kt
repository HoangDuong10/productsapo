package com.example.product.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Image : Serializable {
    @SerializedName("full_path")
    lateinit var fullPath:String
}