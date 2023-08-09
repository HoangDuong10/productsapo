package com.example.product.ui.detailProduct

import com.example.product.di.ApiModule
import com.example.product.model.Product
import com.example.product.model.ResponseProduct
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailProductPresenter(var mDetailProductContracts: DetailProductContracts) {
    var disposable : Disposable?=null
    private var mProduct : ResponseProduct?=null
    fun getDetailProduct(id : Int){
        ApiModule.apiService.getDetailProduct(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseProduct>{
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: ResponseProduct) {
                    mProduct = t
                }

                override fun onError(e: Throwable) {
                    mDetailProductContracts.callApiErreor()
                }

                override fun onComplete() {
                    mProduct?.let { mDetailProductContracts.setData(it.product) }
                }

            })
    }
}