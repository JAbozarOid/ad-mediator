package com.example.admediator.utils.http

import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(
    @SerializedName("adNetworks")
    var results: List<T>
)