package by.slizh.pexelsapp.data.local.repository

import by.slizh.pexelsapp.data.converter.photoToPhotoEntity
import by.slizh.pexelsapp.data.local.PhotoDatabase
import by.slizh.pexelsapp.data.local.dao.BookmarkDao
import by.slizh.pexelsapp.data.local.entity.PhotoEntity
import by.slizh.pexelsapp.data.response.Photo
import javax.inject.Inject

class BookmarkRepository @Inject constructor(
    private val photoDatabase: PhotoDatabase
//    private val bookmarkDao: BookmarkDao
) {

    suspend fun insertPhoto(photo: Photo) = photoDatabase.photoDao().insertPhoto(photoToPhotoEntity(photo))
    fun getPhotoById(id: Int) = photoDatabase.photoDao().getPhotoById(id)
    fun getAllPhotos() = photoDatabase.photoDao().getAllPhotos()
    suspend fun deletePhoto(photo: Photo) = photoDatabase.photoDao().deletePhoto(photoToPhotoEntity(photo))
}