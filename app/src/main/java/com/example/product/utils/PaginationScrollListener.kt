package com.example.product.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(var linearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        //số item nhìn thấy trên màn hình
        val  visibleItemCount : Int = linearLayoutManager.childCount
        //toongr số item 1 page
        var  tottalItemCount = linearLayoutManager.itemCount
        //item nhìn thấy đầu tiên
        val firstVisibleitemPosition = linearLayoutManager.findFirstVisibleItemPosition()
        if(isLoading()||isLastPage()){
            return
        }
        if(firstVisibleitemPosition>=0 && (visibleItemCount+firstVisibleitemPosition)>=tottalItemCount){
            loadMoreItem()
        }
    }

    abstract fun loadMoreItem();
    abstract fun isLoading() : Boolean;
    abstract fun isLastPage() : Boolean

}