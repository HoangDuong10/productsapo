package com.example.product.ui.detailVariant
import com.example.product.di.ApiModule
import com.example.product.model.ResponseProduct
import com.example.product.model.ResponseVariant
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailVariantPresenter(var mDetailVariantContracts: DetailVariantContracts) {
    var variantDisposable: Disposable? = null
    var productDisposable: Disposable? = null
    private var mVariant : ResponseVariant?=null
    private var mProduct : ResponseProduct?=null
    fun getDetailVariant(productId:Int,variantId: Int){
        ApiModule.apiService.getDetailVariant(productId,variantId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseVariant>{
                override fun onSubscribe(d: Disposable) {
                    variantDisposable = d
                }

                override fun onNext(t: ResponseVariant) {
                    mVariant = t
                }

                override fun onError(e: Throwable) {
                    mDetailVariantContracts.callApiErreor()
                }

                override fun onComplete() {
                    mVariant?.let { mDetailVariantContracts.callDetailVariant(it.variant) }
                }

            })
    }

    fun getDetailProduct(id : Int){
        ApiModule.apiService.getDetailProduct(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseProduct>{
                override fun onSubscribe(d: Disposable) {
                    productDisposable = d
                }

                override fun onNext(t: ResponseProduct) {
                    mProduct = t
                }

                override fun onError(e: Throwable) {
                    mDetailVariantContracts.callApiErreor()
                }

                override fun onComplete() {
                    mProduct?.let { mDetailVariantContracts.callDetailProduct(it.product) }
                }

            })
    }
}