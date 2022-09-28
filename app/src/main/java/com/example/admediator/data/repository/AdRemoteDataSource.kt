package com.example.admediator.data.repository

import com.example.admediator.data.network.AdApi
import com.example.admediator.utils.http.HttpUtil.safeApiCall
import javax.inject.Inject

class AdRemoteDataSource @Inject constructor(
    private var adApi: AdApi
) {

    suspend fun getAdNetworks(
    ) = safeApiCall {
        adApi.getAdNetworks()
    }

}