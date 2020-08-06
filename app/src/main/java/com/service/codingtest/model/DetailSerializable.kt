package com.service.codingtest.model

import java.io.Serializable

internal class DetailSerializable(
    val id: Int,

    val name: String,

    val thumbnail: String,

    val imagePath: String,

    val subject: String,

    val price: Int,

    val rate: Double,

    val saveTime: Long
) : Serializable

