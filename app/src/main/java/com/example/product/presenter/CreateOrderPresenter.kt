package com.example.product.presenter

import com.example.product.api.RetrofitConfig
import com.example.product.api.response.OrderResponse
import com.example.product.api.response.OrderSourceResponse
import com.example.product.presenter.converter.OrderConverter
import com.example.product.presenter.converter.OrderSourceConverter
import com.example.product.ui.createorder.CreateOrderContracts
import com.example.product.ui.model.Order
import com.example.product.ui.model.OrderSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CreateOrderPresenter(var createOrderContracts: CreateOrderContracts) {
    var disposable:Disposable?=null
    var orderSourceResponse: OrderSourceResponse?=null
    fun postOrder(order: Order){
        val orderDTO= order.let { OrderConverter.orderToOrderDTO(it) }
        val orderResponse= OrderResponse(orderDTO)
        RetrofitConfig.apiService.postOrder(orderResponse).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<OrderResponse>{
                override fun onSubscribe(d: Disposable) {
                    disposable=d
                }

                override fun onNext(t: OrderResponse) {
                    createOrderContracts.callAPiSuccuss()
                }

                override fun onError(e: Throwable) {
                   createOrderContracts.callApiError(e.message.toString())
                }

                override fun onComplete() {

                }

            })
    }
    fun getSourceId(){
        RetrofitConfig.apiService.getSourcesId().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<OrderSourceResponse>{
                override fun onSubscribe(d: Disposable) {
                    disposable=d
                }

                override fun onNext(t: OrderSourceResponse) {
                    orderSourceResponse=t
                }

                override fun onError(e: Throwable) {
                    createOrderContracts.callApiError(e.message.toString())
                }

                override fun onComplete() {
                    val listOrderSource = orderSourceResponse?.orderSources?.let { OrderSourceConverter.listOrderSourceDTOToListOrderSource(it)} as MutableList<OrderSource>
                    createOrderContracts.callListSourceId(listOrderSource)
                }

            })
    }
}