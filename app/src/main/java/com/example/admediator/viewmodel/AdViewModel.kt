package com.example.admediator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.admediator.data.repository.AdRepository
import com.example.admediator.model.AdNetworkResModel
import com.example.admediator.utils.http.ApiResponse
import com.example.admediator.utils.http.ConnectionListener
import com.example.admediator.utils.http.GenericResponse
import com.example.admediator.utils.http.HttpUtil.genericRequestCollect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AdViewModel @Inject constructor(
    private val adRepository: AdRepository,
    private val mConnectionListener: ConnectionListener,
) : ViewModel() {

    private var _getAdNetworksList =
        MutableStateFlow<ApiResponse<GenericResponse<AdNetworkResModel>>?>(null)
    val getAdNetworksList: MutableStateFlow<ApiResponse<GenericResponse<AdNetworkResModel>>?> get() = _getAdNetworksList


    fun getAdNetworks() {
        genericRequestCollect(
            body = { adRepository.remote.getAdNetworks() }, mConnectionListener,
            viewModelScope
        ) {
            when (it) {
                is ApiResponse.Success -> {
                    _getAdNetworksList.value = ApiResponse.Success(data = it.data)
                }
                else -> {
                    _getAdNetworksList.value = it
                }
            }
        }
    }
}