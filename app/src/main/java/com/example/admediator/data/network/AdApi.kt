package com.example.admediator.data.network

import com.example.admediator.model.AdNetworkResModel
import com.example.admediator.utils.constants.ApiConstants.Companion.AD_NETWORKS
import com.example.admediator.utils.http.GenericResponse
import retrofit2.Response
import retrofit2.http.GET

interface AdApi {

    // Get ad networks \\
    @GET(AD_NETWORKS)
    suspend fun getAdNetworks(
    ): Response<GenericResponse<AdNetworkResModel>>
}