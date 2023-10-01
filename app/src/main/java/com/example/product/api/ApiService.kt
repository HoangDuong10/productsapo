package com.example.product.api
import com.example.product.model.ProductResponse
import com.example.product.model.VariantResponse
import com.example.product.api.response.OrderResponse
import com.example.product.api.response.OrderSourceResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(ApiConfig.PRODUCT_LIST_URL)
    fun getListProduct(@Query("page") page : Int,@Query("limit") limit : Int,@Query("query") query: String) : Observable<ProductResponse>

    @GET(ApiConfig.VARIANT_LIST_URL)
    fun getListVariants(@Query("page") page: Int,@Query("limit") limit : Int,@Query("query") query: String) : Observable<VariantResponse>

    @GET(ApiConfig.PRODUCT_URL)
    fun getDetailProduct(@Path("id") id: Int) : Observable<ProductResponse>

    @GET(ApiConfig.VARIANT_URL)
    fun getDetailVariant(@Path("product_id") productId: Int,@Path("variant_id") variantId: Int) : Observable<VariantResponse>

    @GET(ApiConfig.PRODUCT_LIST_URL)
    fun findProduct(@Query("query") query: String) : Observable<ProductResponse>

    @GET(ApiConfig.VARIANT_LIST_URL)
    fun findVariant(@Query("query") query :String) :Observable<VariantResponse>

    @Headers("X-Sapo-Client: iOS"
        ,"X-Sapo-LocationId:286483","Content-Type:application/json")
    @POST("admin/orders.json")
    fun postOrder(@Body order: OrderResponse):Observable<OrderResponse>

    @GET("admin/order_sources.json")
    fun getSourcesId ():Observable<OrderSourceResponse>

}