package by.slizh.pexelsapp.data.response.repository

import by.slizh.pexelsapp.api.PexelsApi
import by.slizh.pexelsapp.data.response.FeaturedCollection
import by.slizh.pexelsapp.data.response.Photo
import by.slizh.pexelsapp.data.response.PhotoList
import retrofit2.Response
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val pexelsApi: PexelsApi,
) {

    suspend fun getCuratedPhotoList(): Response<PhotoList> {
        return pexelsApi.getCuratedPhotoList()
    }

    suspend fun getPhotoById(photoId: Int): Response<Photo> {
        return pexelsApi.getPhotoById(photoId)
    }

    suspend fun getSearchPhotoList(query: String): Response<PhotoList> {
        return pexelsApi.getSearchPhotoList(query)
    }

    suspend fun getFeaturedCollections(): Response<FeaturedCollection> {
        return pexelsApi.getFeaturedCollections()
    }
}