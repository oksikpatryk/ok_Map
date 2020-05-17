package com.oksik.okmap.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oksik.okmap.model.LikedPlant

@Database(entities = [LikedPlant::class], version = 2, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {
    abstract val plantDatabaseDao: PlantDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        fun getInstance(context: Context): PlantDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlantDatabase::class.java,
                        "plant_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}