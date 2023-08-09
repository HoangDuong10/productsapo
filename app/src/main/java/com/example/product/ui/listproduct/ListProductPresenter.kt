package com.example.product.ui.listproduct

import com.example.product.di.ApiModule
import com.example.product.model.ResponseProduct
import com.example.product.model.ResponseVariant
import com.example.product.utils.Constant
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ListProductPresenter (val listProductContracts: ListProductContracts){
    private var responseVariant : ResponseVariant?=null
    private var responseProduct : ResponseProduct?=null
    var variantDisposable: Disposable? = null
    var productDisposable: Disposable? = null

    fun getListProduct(currentPage : Int){
       ApiModule.apiService
        .getListProduct(currentPage,Constant.limit).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseProduct>{
                override fun onSubscribe(d: Disposable) {
                    productDisposable=d
                }

                override fun onNext(t: ResponseProduct) {
                    responseProduct = t
                }

                override fun onError(e: Throwable) {
                    listProductContracts.callApiErreor()
                }

                override fun onComplete() {
                    responseProduct?.let { listProductContracts.setListProduct(it.products,it.metadata) }

                }

            })
    }

    fun getListVariant (currentPage : Int){
       ApiModule.apiService.getListVariants(currentPage,Constant.limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseVariant>{
                override fun onSubscribe(d: Disposable) {
                    variantDisposable= d
                }

                override fun onNext(t: ResponseVariant) {
                    responseVariant = t
                }

                override fun onError(e: Throwable) {
                    listProductContracts.callApiErreor()
                }

                override fun onComplete() {
                    responseVariant?.let { listProductContracts.setListVariant(it.variants,it.metadata) }
                }

            })
    }

}