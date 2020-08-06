package com.service.codingtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.service.codingtest.model.FavoriteEntity

class MainViewModel : BaseViewModel() {
    val inputNumber = MutableLiveData<Int>()
//    private lateinit var viewModelCallback: ViewModelCallback
//
//    fun AddInfoFinalViewModel(callback: ViewModelCallback) {
//        viewModelCallback = callback
//    }
//
//    interface ViewModelCallback {
//        fun onClickFinal()
//    }

//    val config = PagedList.Config.Builder()
//        .setInitialLoadSizeHint(20)
//        .setPageSize(3)
//        .setEnablePlaceholders(true)
//        .build()
//
//    val liveDataProduct = LivePagedListBuilder<String, ProductData>(object : DataSource.Factory<String, ProductData>() {
//        override fun create(): DataSource<String, ProductData> {
//            return ProductDataSource()
//        }
//    }, config).build()

    var list : LiveData<List<FavoriteEntity>> = liveData {  }


}