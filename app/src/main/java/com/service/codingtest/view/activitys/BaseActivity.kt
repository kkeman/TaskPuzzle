package com.service.codingtest.view.activitys

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.service.codingtest.network.MLog
import io.reactivex.disposables.CompositeDisposable

open class BaseActivity : AppCompatActivity() {

    override fun onDestroy() {
        MLog.d("BaseActivity", "onDestroy")

        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
    }
}