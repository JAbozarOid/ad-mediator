package com.example.admediator.utils.http

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.admediator.utils.constants.ApiConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

object HttpUtil {

    suspend inline fun <T> safeApiCall(
        crossinline body: suspend () -> Response<T>
    ): ApiResponse<T> {
        return try {
            val response = withContext(Dispatchers.IO) {
                body()
            }
            ApiResponse.create(response)
        } catch (e: Exception) {
            ApiResponse.create(e)
        }
    }

    suspend inline fun <T> safeApiCallLiveData(
        crossinline body: suspend () -> Response<T>
    ): LiveData<ApiResponse<T>> {
        val responseLiveData = MutableLiveData<ApiResponse<T>>()
        try {
            // blocking block
            val response = withContext(Dispatchers.IO) {
                body()
            }
            responseLiveData.postValue(ApiResponse.create(response))
        } catch (e: Exception) {
            responseLiveData.postValue(ApiResponse.create(e))
        }
        return responseLiveData
    }

    inline fun <T> genericRequestCollect(
        crossinline body: suspend () -> ApiResponse<T>,
        networkListener: NetworkListener,
        coroutineScope: CoroutineScope,
        crossinline collectFunction: (ApiResponse<T>) -> Unit
    ) = coroutineScope.launch(Dispatchers.IO) {
        flowResponse(
            body = body,
            networkListener = networkListener
        ).collect {
            collectFunction(it)
        }
    }

    inline fun <T> flowResponse(
        crossinline body: suspend () -> ApiResponse<T>,
        networkListener: NetworkListener
    ) = flow {
        if (networkListener.isConnected) {
            emit(ApiResponse.Loading())
            val result = body()
            emit(result)
        } else {
            emit(ApiResponse.ErrorTryAgain(ApiConstants.ERROR_NO_NETWORK))
        }
    }.flowOn(Dispatchers.IO)
}