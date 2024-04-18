package by.slizh.pexelsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import by.slizh.pexelsapp.data.local.dao.PhotoDao
import by.slizh.pexelsapp.data.local.entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

}