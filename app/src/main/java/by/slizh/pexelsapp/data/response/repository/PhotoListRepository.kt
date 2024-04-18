package by.slizh.pexelsapp.data.response.repository

import androidx.lifecycle.LiveData
import by.slizh.pexelsapp.api.PexelsApi
import by.slizh.pexelsapp.data.response.PhotoList
import retrofit2.Response
import javax.inject.Inject

class PhotoListRepository @Inject constructor(
    private val pexelsApi: PexelsApi,
) {

    suspend fun getCuratedPhotoList(): Response<PhotoList> {
        return pexelsApi.getCuratedPhotoList()
    }
}