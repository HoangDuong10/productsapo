package com.example.product.ui.selectvariant

import com.example.product.di.ApiModule
import com.example.product.model.ResponseVariant
import com.example.product.model.Variants
import com.example.product.utils.Constant
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SelectVariantPresenter(val mSelectVariantContracts: SelectVariantContracts) {
    private var responseVariant : ResponseVariant?=null
    var mDisposable : Disposable?= null
    fun getListVariant (currentPage : Int){
        ApiModule.apiService.getListVariants(currentPage, Constant.limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseVariant> {
                override fun onSubscribe(d: Disposable) {
                    mDisposable= d
                }

                override fun onNext(t: ResponseVariant) {
                    responseVariant = t
                }

                override fun onError(e: Throwable) {
                    mSelectVariantContracts.callApiErreor()
                }

                override fun onComplete() {
                    responseVariant?.let { mSelectVariantContracts.setListVariant(it.variants,it.metadata) }
                }

            })
    }
}