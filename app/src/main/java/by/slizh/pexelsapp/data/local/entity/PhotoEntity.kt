package by.slizh.pexelsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.slizh.pexelsapp.data.response.Src

@Entity(tableName = "photo_table")
data class PhotoEntity(
    @PrimaryKey val id: Int,
    val alt: String,
    val avg_color: String,
    val height: Int,
    val width: Int,
    val liked: Boolean,
    val photographer: String,
    val photographer_id: Int,
    val photographer_url: String,
    val src: SrcEntity,
    val url: String
)

