package com.service.codingtest.manager

import androidx.lifecycle.LiveData
import androidx.room.*
import com.service.codingtest.model.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: FavoriteEntity)

    @Delete
    fun delete(items: FavoriteEntity)

    @Update
    fun update(items: FavoriteEntity) : Int

    @Query("SELECT * FROM Favorite ORDER BY saveTime ASC")
    fun loadAllSortSaveTime(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM Favorite ORDER BY rate DESC")
    fun loadAllSortRate(): LiveData<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT * FROM Favorite WHERE id=:id)")
    fun exist(id: Int) : Boolean
}
