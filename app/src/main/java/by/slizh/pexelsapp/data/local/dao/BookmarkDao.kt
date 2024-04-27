package by.slizh.pexelsapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.slizh.pexelsapp.data.local.entity.PhotoEntity

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PhotoEntity)

    @Query("SELECT * FROM photo_table WHERE id=:id")
    fun getPhotoById(id: Int): LiveData<List<PhotoEntity>>

    @Query("SELECT * FROM photo_table ")
     fun getAllPhotos(): LiveData<List<PhotoEntity>>

    @Delete
    suspend fun deletePhoto(photo: PhotoEntity)
}