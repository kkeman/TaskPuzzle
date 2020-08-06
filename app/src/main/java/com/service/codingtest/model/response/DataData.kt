package com.service.codingtest.model.response

import com.google.gson.annotations.SerializedName

data class DataData(
    @SerializedName("totalCount")
    val totalCount: Int,

    @SerializedName("product")
    val product: MutableList<ProductData>
)