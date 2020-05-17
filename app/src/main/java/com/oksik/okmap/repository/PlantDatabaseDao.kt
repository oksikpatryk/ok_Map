package com.oksik.okmap.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.oksik.okmap.model.LikedPlant

@Dao
interface PlantDatabaseDao {
    @Insert
    fun insertPlant(likedPlant: LikedPlant)

    @Query("SELECT * FROM liked_plant")
    fun getAll(): LiveData<List<LikedPlant>>
}