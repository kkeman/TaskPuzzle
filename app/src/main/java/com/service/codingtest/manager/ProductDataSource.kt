package com.service.codingtest.manager

import androidx.paging.ItemKeyedDataSource
import com.service.codingtest.model.response.ProductData
import com.service.codingtest.network.HttpClient
import com.service.codingtest.network.MLog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


class ProductDataSource : ItemKeyedDataSource<String, ProductData>() {

    var mTAG = ProductDataSource::class.java.name
    var mPage: Int = 1
    var cDisposable = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<ProductData>
    ) {
        HttpClient(mPage++.toString() + ".json").enqueue().subscribe {
            MLog.e(mTAG, "loadInitial List size:${it.data.product.size}")
            callback.onResult(it.data.product)
        }.addTo(cDisposable)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<ProductData>) {
        HttpClient(mPage++.toString() + ".json").enqueue().subscribe {
            MLog.e(mTAG, "loadAfter List size:${it.data.product.size}")
            callback.onResult(it.data.product)
        }.addTo(cDisposable)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<ProductData>) {
        MLog.e(mTAG, "loadAfter")
    }

    override fun getKey(item: ProductData): String {
        return item.id.toString()
    }

    fun clear() {
        cDisposable.clear()
    }
}