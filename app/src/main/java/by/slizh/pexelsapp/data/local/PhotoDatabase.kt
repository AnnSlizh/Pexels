package by.slizh.pexelsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.slizh.pexelsapp.data.local.dao.BookmarkDao
import by.slizh.pexelsapp.data.local.entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1)
@TypeConverters(SrcEntityConverter::class)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao(): BookmarkDao

}