package com.service.codingtest.model.response

import com.google.gson.annotations.SerializedName

data class ProductData(
        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("thumbnail")
        val thumbnail: String,

        @SerializedName("description")
        val description: DescriptionData,

        @SerializedName("rate")
        val rate: Double,

        var isFavorite: Boolean
) {
    data class DescriptionData(
            @SerializedName("imagePath")
            val imagePath: String,

            @SerializedName("subject")
            val subject: String,

            @SerializedName("price")
            val price: Int
    )
}