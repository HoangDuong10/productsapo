package com.example.product.presenter


import com.example.product.api.RetrofitConfig
import com.example.product.ui.model.Product
import com.example.product.model.ProductResponse
import com.example.product.ui.detailProduct.DetailProductContracts
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailProductPresenter(var mDetailProductContracts: DetailProductContracts) {
    var disposable : Disposable?=null
    private var productResponse : ProductResponse?=null
    fun getDetailProduct(id : Int){
        RetrofitConfig.apiService.getDetailProduct(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ProductResponse>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: ProductResponse) {
                    productResponse = t
                }

                override fun onError(e: Throwable) {
                    mDetailProductContracts.callApiErreor(e.message.toString())
                }

                override fun onComplete() {
                    val product= productResponse?.product?.let { ProductConverter.productDTOToProduct(it) } as Product
                    mDetailProductContracts.callProduct(product)
                }

            })
    }
}