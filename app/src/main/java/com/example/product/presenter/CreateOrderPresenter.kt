package com.example.product.presenter

import com.example.product.api.ApiConfig
import com.example.product.api.response.OrderResponse
import com.example.product.api.response.OrderSourceResponse
import com.example.product.ui.createorder.CreateOrderContracts
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CreateOrderPresenter(var createOrderContracts: CreateOrderContracts) {
    var disposable:Disposable?=null
    var orderSourceResponse: OrderSourceResponse?=null
    fun postOrder(orderResponse: OrderResponse){
        ApiConfig.apiService.postOrder(orderResponse).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<OrderResponse>{
                override fun onSubscribe(d: Disposable) {
                    disposable=d
                }

                override fun onNext(t: OrderResponse) {
                    createOrderContracts.callAPiSuccuss()
                }

                override fun onError(e: Throwable) {
                   createOrderContracts.callApiError()
                }

                override fun onComplete() {

                }

            })
    }
    fun getSourceId(){
        ApiConfig.apiService.getSourcesId().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<OrderSourceResponse>{
                override fun onSubscribe(d: Disposable) {
                    disposable=d
                }

                override fun onNext(t: OrderSourceResponse) {
                    orderSourceResponse=t
                }

                override fun onError(e: Throwable) {
                    createOrderContracts.callApiError()
                }

                override fun onComplete() {
                    orderSourceResponse?.order_sources?.let { createOrderContracts.callListSourceId(it)
                    }
                }

            })
    }
}