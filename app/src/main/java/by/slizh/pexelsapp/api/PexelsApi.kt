package by.slizh.pexelsapp.api

import by.slizh.pexelsapp.data.response.FeaturedCollection
import by.slizh.pexelsapp.data.response.Photo
import by.slizh.pexelsapp.data.response.PhotoList
import by.slizh.pexelsapp.util.Constans.Companion.API_KEY
import by.slizh.pexelsapp.util.Constans.Companion.PER_PAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApi {

    @GET("search")
    suspend fun getSearchPhotoList(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = PER_PAGE,
        @Query("page") page: Int = 1,
        @Header("Authorization") authorization: String = API_KEY
    ): Response<PhotoList>

    @GET("curated")
    suspend fun getCuratedPhotoList(
        @Query("per_page") perPage: Int = PER_PAGE,
        @Query("page") page: Int = 1,
        @Header("Authorization") authorization: String = API_KEY
    ): Response<PhotoList>

    @GET("photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String = API_KEY
    ): Response<Photo>

    @GET("collections/featured")
    suspend fun getFeaturedCollections(
        @Query("per_page") perPage: Int = PER_PAGE,
        @Header("Authorization") authorization: String = API_KEY
    ): Response<FeaturedCollection>
}