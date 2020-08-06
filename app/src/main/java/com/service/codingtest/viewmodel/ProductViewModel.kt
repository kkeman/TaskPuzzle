package com.service.codingtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.service.codingtest.manager.ProductDataSource
import com.service.codingtest.model.response.ProductData

class ProductViewModel() : BaseViewModel() {

    private var query: String = ""

    fun onQueryChange(query: CharSequence) {
        this.query = query.toString()
    }

    val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(20)
        .setPageSize(3)
        .setEnablePlaceholders(true)
        .build()

    val productDataSource = ProductDataSource()

    val liveDataProduct = LivePagedListBuilder<String, ProductData>(object : DataSource.Factory<String, ProductData>() {
        override fun create(): DataSource<String, ProductData> {
            return productDataSource
        }
    }, config).build()

    override fun onCleared() {
        super.onCleared()
        productDataSource.clear()
    }

}