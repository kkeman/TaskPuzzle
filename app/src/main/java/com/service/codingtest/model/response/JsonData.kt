package com.service.codingtest.model.response

import com.google.gson.annotations.SerializedName
import com.service.codingtest.model.response.DataData

data class JsonData(
        @SerializedName("msg")
        val msg: String,

        @SerializedName("data")
        val data: DataData,

        @SerializedName("code")
        val code: Int
)