package com.example.admediator.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class AdNetworkResModel(

    @SerializedName("id") var id: String? = "",
    @SerializedName("name") var name: String? = "",

    ) : Parcelable
