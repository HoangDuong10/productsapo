package com.example.product.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(var linearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val  visibleItemCount : Int = linearLayoutManager.childCount
        var  tottalItemCount = linearLayoutManager.itemCount
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