package com.example.product.presenter

import com.example.product.presenter.converter.MetaDataConverter

import com.example.product.presenter.converter.VariantConverter
import com.example.product.api.RetrofitConfig
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
import io.reactivex.rxjava3.subjects.PublishSubject

class ListProductPresenter (val listProductContracts: ListProductContracts){
    private var variantResponse : VariantResponse?=null
    private var productResponse : ProductResponse?=null
    var disposable: Disposable? = null

    fun getListProduct(currentPage : Int,query: String){
       RetrofitConfig.apiService
        .getListProduct(currentPage, AppConfig.limit,query).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ProductResponse>{
                override fun onSubscribe(d: Disposable) {
                    disposable=d
                }
                override fun onNext(t: ProductResponse) {
                    productResponse = t
                }
                override fun onError(e: Throwable) {
                    listProductContracts.callApiError(e.message.toString())
                }
                override fun onComplete() {
                    val products= productResponse?.products?.let { ProductConverter.listProductDTOToListProduct(it) }as MutableList<Product>
                    val metaData= productResponse?.metadata?.let { MetaDataConverter.metaDataDTOToMetaData(it) } as MetaData
                    listProductContracts.callListProduct(products,metaData)
                }
            })
    }
    fun getListVariant (currentPage : Int,query:String){
       RetrofitConfig.apiService.getListVariants(currentPage, AppConfig.limit,query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<VariantResponse>{
                override fun onSubscribe(d: Disposable) {
                    disposable= d
                }
                override fun onNext(t: VariantResponse) {
                    variantResponse = t
                }
                override fun onError(e: Throwable) {
                    listProductContracts.callApiError(e.message.toString())
                }
                override fun onComplete() {
                    val variants= variantResponse?.variants?.let { VariantConverter.listVariantDTOtoListVariant(it) }as MutableList<Variant>
                    val metadata= variantResponse?.metadata?.let { MetaDataConverter.metaDataDTOToMetaData(it) } as MetaData
                    listProductContracts.callListVariant(variants,metadata)
                }
            })
    }


//    fun findProduct(query:String){
//        RetrofitConfig.apiService.findProduct(query).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Observer<ProductResponse> {
//                override fun onSubscribe(d: Disposable) {
//                    productDisposable=d
//                }
//
//                override fun onNext(t: ProductResponse) {
//                    productResponse=t
//                }
//
//                override fun onError(e: Throwable) {
//                    listProductContracts.callApiError(e.message.toString())
//                }
//
//                override fun onComplete() {
//                    val products= productResponse?.products?.let { ProductConverter.listProductDTOToListProduct(it) }as MutableList<Product>
//                    val metaData= productResponse?.metadata?.let { MetaDataConverter.metaDataDTOToMetaData(it) } as MetaData
//                    listProductContracts.callListProduct(products,metaData)
//                }
//
//            })
//    }
//    fun findVariant(query:String){
//        RetrofitConfig.apiService.findVariant(query).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object :Observer<VariantResponse>{
//                override fun onSubscribe(d: Disposable) {
//                    productDisposable=d
//                }
//
//                override fun onNext(t: VariantResponse) {
//                    variantResponse=t
//                }
//
//                override fun onError(e: Throwable) {
//                    listProductContracts.callApiError(e.message.toString())
//                }
//
//                override fun onComplete() {
//                    val variants= variantResponse?.variants?.let { VariantConverter.listVariantDTOtoListVariant(it) }as MutableList<Variant>
//                    val metadata= variantResponse?.metadata?.let { MetaDataConverter.metaDataDTOToMetaData(it) } as MetaData
//                    listProductContracts.callListVariant(variants,metadata)
//                }
//            })
//
//    }

}