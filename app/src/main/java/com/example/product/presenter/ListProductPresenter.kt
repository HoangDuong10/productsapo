package com.example.product.presenter

import com.example.product.converter.MetaDataConverter

import com.example.product.converter.VariantConverter
import com.example.product.api.ApiConfig
import com.example.product.model.*
import com.example.product.ui.listproduct.ListProductContracts
import com.example.product.ui.AppConfig
import com.example.product.ui.model.MetaData
import com.example.product.ui.model.Product
import com.example.product.ui.model.Variant
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ListProductPresenter (val listProductContracts: ListProductContracts){
    private var variantResponse : VariantResponse?=null
    private var productResponse : ProductResponse?=null
    var productDisposable: Disposable? = null

    fun getListProduct(currentPage : Int){
       ApiConfig.apiService
        .getListProduct(currentPage, AppConfig.limit).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ProductResponse>{
                override fun onSubscribe(d: Disposable) {
                    productDisposable=d
                }
                override fun onNext(t: ProductResponse) {
                    productResponse = t
                }
                override fun onError(e: Throwable) {
                    listProductContracts.callApiErreor()
                }
                override fun onComplete() {
                    val products= productResponse?.products?.let { ProductConverter.toModelList(it) }as MutableList<Product>
                    val metadata= productResponse?.metadata?.let { MetaDataConverter.toModel(it) } as MetaData
                    listProductContracts.callListProduct(products,metadata)
                }
            })
    }
    fun getListVariant (currentPage : Int){
       ApiConfig.apiService.getListVariants(currentPage, AppConfig.limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<VariantResponse>{
                override fun onSubscribe(d: Disposable) {
                    productDisposable= d
                }
                override fun onNext(t: VariantResponse) {
                    variantResponse = t
                }
                override fun onError(e: Throwable) {
                    listProductContracts.callApiErreor()
                }
                override fun onComplete() {
                    val variants= variantResponse?.variants?.let { VariantConverter.toModelList(it) }as MutableList<Variant>
                    val metadata= variantResponse?.metadata?.let { MetaDataConverter.toModel(it) } as MetaData
                    listProductContracts.callListVariant(variants,metadata)
                }
            })
    }
    fun findProduct(query:String){
        ApiConfig.apiService.findProduct(query).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ProductResponse> {
                override fun onSubscribe(d: Disposable) {
                    productDisposable=d
                }

                override fun onNext(t: ProductResponse) {
                    productResponse=t
                }

                override fun onError(e: Throwable) {
                    listProductContracts.callApiErreor()
                }

                override fun onComplete() {
                    val products= productResponse?.products?.let { ProductConverter.toModelList(it) }as MutableList<Product>
                    val metadata= productResponse?.metadata?.let { MetaDataConverter.toModel(it) } as MetaData
                    listProductContracts.callListProduct(products,metadata)
                }

            })
    }
    fun findVariant(query:String){
        ApiConfig.apiService.findVariant(query).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<VariantResponse>{
                override fun onSubscribe(d: Disposable) {
                    productDisposable=d
                }

                override fun onNext(t: VariantResponse) {
                    variantResponse=t
                }

                override fun onError(e: Throwable) {
                    listProductContracts.callApiErreor()
                }

                override fun onComplete() {
                    val variants= variantResponse?.variants?.let { VariantConverter.toModelList(it) }as MutableList<Variant>
                    val metadata= variantResponse?.metadata?.let { MetaDataConverter.toModel(it) } as MetaData
                    listProductContracts.callListVariant(variants,metadata)
                }
            })

    }

}