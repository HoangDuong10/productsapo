package com.example.product.presenter

import com.example.product.presenter.converter.MetaDataConverter
import com.example.product.presenter.converter.VariantConverter
import com.example.product.api.RetrofitConfig
import com.example.product.ui.model.MetaData
import com.example.product.model.VariantResponse
import com.example.product.ui.selectvariant.SelectVariantContracts
import com.example.product.ui.AppConfig
import com.example.product.ui.model.OrderLineItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SelectVariantPresenter(val selectVariantContracts: SelectVariantContracts) {
    private var variantResponse : VariantResponse?=null
    var disposable : Disposable?= null
    fun getListVariant (currentPage : Int,query: String){
        RetrofitConfig.apiService.getListVariants(currentPage, AppConfig.limit,query)
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
                    selectVariantContracts.callApiErreor(e.message.toString())
                }

                override fun onComplete() {
                    val orderLineItem = variantResponse?.variants?.let { VariantConverter.listVariantDTOtoListOrderLineItem(it) } as MutableList<OrderLineItem>
                    val metadata = variantResponse?.metadata?.let { MetaDataConverter.metaDataDTOToMetaData(it) } as MetaData
                    selectVariantContracts.setListVariant(orderLineItem, metadata)
                }
            })
    }
    fun findVariant(query:String){
        RetrofitConfig.apiService.findVariant(query).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<VariantResponse>{
                override fun onSubscribe(d: Disposable) {
                    disposable=d
                }

                override fun onNext(t: VariantResponse) {
                    variantResponse=t
                }

                override fun onError(e: Throwable) {
                    selectVariantContracts.callApiErreor(e.message.toString())
                }

                override fun onComplete() {
                    val orderLineItem = variantResponse?.variants?.let { VariantConverter.listVariantDTOtoListOrderLineItem(it) } as MutableList<OrderLineItem>
                    val metadata = variantResponse?.metadata?.let { MetaDataConverter.metaDataDTOToMetaData(it) } as MetaData
                    selectVariantContracts.setListVariant(orderLineItem, metadata)
                }
            })

    }
}