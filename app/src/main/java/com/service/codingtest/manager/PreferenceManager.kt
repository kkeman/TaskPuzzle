package com.service.codingtest.manager

import android.content.Context
import android.content.SharedPreferences
import com.service.codingtest.model.FavoriteSortTypeEnum

class PreferenceManager(context: Context?) {
    var prefKeyFavoriteSort: FavoriteSortTypeEnum
        get() {
            val sRecentSearch = mPref!!.getString(PREF_KEY_FAVORITE_SORT, FavoriteSortTypeEnum.LATEST.toString())
            return FavoriteSortTypeEnum.toEnum(sRecentSearch!!)
        }
        set(myEnum) {
            mPref!!.edit().putString(PREF_KEY_FAVORITE_SORT, myEnum.toString()).apply()
        }

    companion object {
        private var preferencesManager: PreferenceManager? = null
        private var mContext: Context? = null
        private var mPref: SharedPreferences? = null
        private const val PREF_KEY_FAVORITE_SORT = "PREF_KEY_FAVORITE_SORT"

        @Synchronized
        fun getInstance(context: Context?): PreferenceManager? {
            if (preferencesManager == null) {
                preferencesManager = PreferenceManager(context)
            }
            return preferencesManager
        }
    }

    init {
        mContext = context
        if (mContext != null) if (mPref == null) mPref =
            mContext!!.getSharedPreferences("CodingTestPreference", 0)
    }
}