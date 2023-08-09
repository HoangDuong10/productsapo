package com.example.product.api

import com.example.product.model.ResponseProduct
import com.example.product.model.ResponseVariant
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("admin/products.json")
    fun getListProduct(@Query("page") page : Int,@Query("limit") limit : Int) : Observable<ResponseProduct>

    @GET("admin/variants/search.json")
    fun getListVariants(@Query("page") page: Int,@Query("limit") limit : Int) : Observable<ResponseVariant>

    @GET("admin/products/{id}.json")
    fun getDetailProduct(@Path("id") id: Int) : Observable<ResponseProduct>

    @GET("admin/products/{product_id}/variants/{variant_id}.json")
    fun getDetailVariant(@Path("product_id") productId: Int,@Path("variant_id") variantId: Int) : Observable<ResponseVariant>

}