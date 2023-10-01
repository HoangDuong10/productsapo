package com.example.product.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImageDTO {
    var id:Int?=null
    @SerializedName("full_path")
    var fullPath:String?=null
}