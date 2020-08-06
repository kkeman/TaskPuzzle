package com.service.codingtest.manager

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.service.codingtest.model.response.JsonData

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmark: JsonData)

    @Delete
    fun delete(bookmark: JsonData)

}