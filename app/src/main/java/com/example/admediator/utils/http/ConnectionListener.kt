package com.example.admediator.utils.http

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ConnectionListener @Inject constructor() :
    LiveData<ConnectionModel?>() {

    init {
        networkMonitor()
    }

    /*
        todo: make it false later after test
     */
    var isConnected = true

    @SuppressLint("CheckResult")
    private fun networkMonitor() {
        val settings = InternetObservingSettings.builder()
            .initialInterval(100)
            .host("www.google.com")
            .strategy(SocketInternetObservingStrategy())
            .build()

        ReactiveNetwork
            .observeInternetConnectivity(settings)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToHost: Boolean ->
                isConnected = isConnectedToHost
                value = ConnectionModel(isConnected)

            }


    }

}
