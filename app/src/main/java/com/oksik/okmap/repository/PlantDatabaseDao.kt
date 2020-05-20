package com.oksik.okmap.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.oksik.okmap.model.LikedPlant

@Dao
interface PlantDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPlant(likedPlant: LikedPlant)

    @Query("SELECT * FROM liked_plant")
    fun getAll(): LiveData<List<LikedPlant>>

    @Query("DELETE FROM liked_plant")
    fun deleteAllPlants()
}