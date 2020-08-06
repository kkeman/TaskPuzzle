package com.service.codingtest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
data class FavoriteEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int,

        val name: String,

        val thumbnail: String,

        val imagePath: String,

        val subject: String,

        val price: Int,

        val rate: Double,

        val saveTime: Long
)