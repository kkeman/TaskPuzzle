package com.service.codingtest

import android.app.Application
//import com.service.codingtest.di.apiModule
//import com.service.codingtest.di.networkModule
//import com.service.codingtest.di.roomModule
//import com.service.codingtest.di.viewModelModule
import org.koin.core.context.startKoin

class PuzzleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
//            networkModule
//            apiModule
//            roomModule
//            viewModelModule
        }
    }
}