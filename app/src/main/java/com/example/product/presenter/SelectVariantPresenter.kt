package com.example.product.presenter

import com.example.product.converter.MetaDataConverter
import com.example.product.converter.VariantConverter
import com.example.product.api.ApiConfig
import com.example.product.ui.model.MetaData
import com.example.product.ui.model.Variant
import com.example.product.model.VariantResponse
import com.example.product.ui.selectvariant.SelectVariantContracts
import com.example.product.ui.AppConfig
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SelectVariantPresenter(val selectVariantContracts: SelectVariantContracts) {
    private var variantResponse : VariantResponse?=null
    var disposable : Disposable?= null
    fun getListVariant (currentPage : Int){
        ApiConfig.apiService.getListVariants(currentPage, AppConfig.limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<VariantResponse> {
                override fun onSubscribe(d: Disposable) {
                    disposable= d
                }

                override fun onNext(t: VariantResponse) {
                    variantResponse = t
                }

                override fun onError(e: Throwable) {
                    selectVariantContracts.callApiErreor()
                }

                override fun onComplete() {
                    val variants = variantResponse?.variants?.let { VariantConverter.toModelList(it) } as MutableList<Variant>
                    val metadata = variantResponse?.metadata?.let { MetaDataConverter.toModel(it) } as MetaData
                    selectVariantContracts.setListVariant(variants, metadata)
                }
            })
    }
    fun findVariant(query:String){
        ApiConfig.apiService.findVariant(query).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<VariantResponse>{
                override fun onSubscribe(d: Disposable) {
                    disposable=d
                }

                override fun onNext(t: VariantResponse) {
                    variantResponse=t
                }

                override fun onError(e: Throwable) {
                    selectVariantContracts.callApiErreor()
                }

                override fun onComplete() {
                    val variants= variantResponse?.variants?.let { VariantConverter.toModelList(it) }as MutableList<Variant>
                    val metadata= variantResponse?.metadata?.let { MetaDataConverter.toModel(it) } as MetaData
                    selectVariantContracts.setListVariant(variants, metadata)
                }
            })

    }
}