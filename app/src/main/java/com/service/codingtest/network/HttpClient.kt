package com.service.codingtest.network

import android.os.Bundle
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.service.codingtest.model.response.JsonData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap


class HttpClient(val mUrl: String) {
    private val mBundle: Bundle
    var mQueryMap: MutableMap<String, String> = HashMap()
    var cDisposable = CompositeDisposable()

    interface HttpClientService {
        @GET("{owner}")
        fun getAPI(
            @Path("owner") owner: String,
            @QueryMap options: Map<String, String>
        ): Observable<JsonData>

        companion object {

            fun getService(): HttpClientService {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level =
                    if (MLog.displayLog) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

                return Retrofit.Builder().apply {
                    baseUrl(Constant.URL_HOME)
                    client(client)
                    addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    addConverterFactory(GsonConverterFactory.create())
                }.build().create(HttpClientService::class.java)
            }
        }
    }

    init {
        mBundle = Bundle()
    }

    fun putQuery(name: String, value: String): HttpClient {
        mQueryMap[name] = value
        return this
    }

    fun enqueue(vararg querys: Pair<String, String>): PublishSubject<JsonData> {

        mQueryMap.putAll(querys)

        val jsonData = PublishSubject.create<JsonData>()
        HttpClientService.getService().getAPI(mUrl, mQueryMap)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.code != 200) Throwable(it.msg)
                else jsonData.onNext(it)

            }, {
//                    if (Util.getNetworkConnect(mContext) == false)
//                        Toast.makeText(mContext, "Please check your network.", Toast.LENGTH_LONG).show()
                cDisposable.dispose()
            }).addTo(cDisposable)
        return jsonData
    }
}