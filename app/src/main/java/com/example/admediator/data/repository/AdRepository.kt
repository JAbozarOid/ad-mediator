package com.example.admediator.data.repository

import javax.inject.Inject

class AdRepository @Inject constructor(
    adRemoteDataSource: AdRemoteDataSource
) {

    val remote = adRemoteDataSource

}