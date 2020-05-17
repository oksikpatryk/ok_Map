package com.oksik.okmap.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "liked_plant")
data class LikedPlant(@PrimaryKey @NotNull var id: String)