package com.service.codingtest.model

enum class FavoriteSortTypeEnum {
    LATEST,

    RATE;
    companion object {
        fun toEnum(myEnumString: String): FavoriteSortTypeEnum {
            return try {
                valueOf(myEnumString)
            } catch (ex: Exception) {
                LATEST
            }
        }
    }
}