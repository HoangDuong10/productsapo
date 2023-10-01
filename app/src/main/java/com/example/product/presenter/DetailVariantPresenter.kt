package com.example.product.presenter
import ProductConverter
import com.example.product.presenter.converter.VariantConverter
import com.example.product.api.RetrofitConfig
import com.example.product.ui.model.Product
import com.example.product.model.ProductResponse
import com.example.product.model.VariantResponse
import com.example.product.ui.model.Variant
import com.example.product.ui.detailVariant.DetailVariantContracts
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailVariantPresenter(var mDetailVariantContracts: DetailVariantContracts) {
    var variantDisposable: Disposable? = null
    private var variantResponse : VariantResponse?=null
    private var productResponse : ProductResponse?=null
    fun getDetailVariant(productId:Int,variantId: Int){
        RetrofitConfig.apiService.getDetailVariant(productId,variantId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<VariantResponse>{
                override fun onSubscribe(d: Disposable) {
                    variantDisposable = d
                }

                override fun onNext(t: VariantResponse) {
                    variantResponse = t
                }

                override fun onError(e: Throwable) {
                    mDetailVariantContracts.callApiError(e.message.toString())
                }

                override fun onComplete() {
                    val variant= variantResponse?.variant?.let { VariantConverter.variantDTOToVariant(it) } as Variant
                    mDetailVariantContracts.callDetailVariant(variant)
                }

            })
    }

    fun getDetailProduct(id : Int){
        RetrofitConfig.apiService.getDetailProduct(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ProductResponse>{
                override fun onSubscribe(d: Disposable) {
                    variantDisposable = d
                }

                override fun onNext(t: ProductResponse) {
                    productResponse = t
                }

                override fun onError(e: Throwable) {
                    mDetailVariantContracts.callApiError(e.message.toString())
                }

                override fun onComplete() {
                    val product= productResponse?.product?.let { ProductConverter.productDTOToProduct(it) } as Product
                    mDetailVariantContracts.callDetailProduct(product)
                }

            })
    }
}