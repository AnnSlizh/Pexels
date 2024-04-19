package by.slizh.pexelsapp.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import by.slizh.pexelsapp.PexelsApp
import by.slizh.pexelsapp.data.response.FeaturedCollection
import by.slizh.pexelsapp.data.response.Photo
import by.slizh.pexelsapp.data.response.PhotoList
import by.slizh.pexelsapp.data.response.repository.PhotoRepository
import by.slizh.pexelsapp.util.Constans.Companion.MESSAGE_ERROR_GET_PHOTO
import by.slizh.pexelsapp.util.Constans.Companion.MESSAGE_NETWORK_FAILED
import by.slizh.pexelsapp.util.Constans.Companion.MESSAGE_NO_INTERNET
import by.slizh.pexelsapp.util.Resource
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val app: Application
) :
    ViewModel() {

    private val _photoList = MutableLiveData<Resource<PhotoList>>()
    val photoList: LiveData<Resource<PhotoList>> = _photoList

    private val _photo = MutableLiveData<Resource<Photo>>()
    val photo: LiveData<Resource<Photo>> = _photo

    private val _featuredCollection = MutableLiveData<Resource<FeaturedCollection>>()
    val featuredCollection: LiveData<Resource<FeaturedCollection>> = _featuredCollection

    fun getSearchPhotoList(query: String) {
        _photoList.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                viewModelScope.launch {
                    val response = photoRepository.getSearchPhotoList(query)
                    _photoList.postValue(photoListResponse(response))
                }
            } else {
                _photoList.postValue(Resource.Error(MESSAGE_NO_INTERNET))
            }
        } catch (t: Throwable) {
            when (t){
                is IOException -> _photoList.postValue(Resource.Error(MESSAGE_NETWORK_FAILED))
                else -> _photoList.postValue(Resource.Error(MESSAGE_ERROR_GET_PHOTO))
            }
        }
    }

    fun getCuratedPhotoList() {
        _photoList.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                viewModelScope.launch {
                    val response = photoRepository.getCuratedPhotoList()
                    _photoList.postValue(photoListResponse(response))
                }
            } else {
                _photoList.postValue(Resource.Error(MESSAGE_NO_INTERNET))
            }
        } catch (t: Throwable) {
            when (t){
                is IOException -> _photoList.postValue(Resource.Error(MESSAGE_NETWORK_FAILED))
                else -> _photoList.postValue(Resource.Error(MESSAGE_ERROR_GET_PHOTO))
            }
        }

    }

    fun getPhotoById(photoId: Int) {
        _photo.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                viewModelScope.launch {
                    val response = photoRepository.getPhotoById(photoId)
                    _photo.postValue(photoResponse(response))
                }
            } else {
                _photo.postValue(Resource.Error(MESSAGE_NO_INTERNET))
            }
        } catch (t: Throwable) {
            when (t){
                is IOException -> _featuredCollection.postValue(Resource.Error(MESSAGE_NETWORK_FAILED))
                else -> _featuredCollection.postValue(Resource.Error(MESSAGE_ERROR_GET_PHOTO))
            }
        }

    }

    fun getFeaturedCollections() {
        _featuredCollection.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                viewModelScope.launch {
                    val response = photoRepository.getFeaturedCollections()
                    _featuredCollection.postValue(featuredCollectionResponse(response))
                }
            } else {
                _featuredCollection.postValue(Resource.Error(MESSAGE_NO_INTERNET))
            }
        } catch (t: Throwable) {
            when (t){
                is IOException -> _featuredCollection.postValue(Resource.Error(MESSAGE_NETWORK_FAILED))
                else -> _featuredCollection.postValue(Resource.Error(MESSAGE_ERROR_GET_PHOTO))
            }
        }
    }

    private fun featuredCollectionResponse(response: Response<FeaturedCollection>): Resource<FeaturedCollection> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return Resource.Success(it)
            }
        }
        return Resource.Error(message = response.errorBody().toString())
    }

    private fun photoListResponse(response: Response<PhotoList>): Resource<PhotoList> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return Resource.Success(it)
            }
        }
        return Resource.Error(message = response.errorBody().toString())
    }

    private fun photoResponse(response: Response<Photo>): Resource<Photo> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return Resource.Success(it)
            }
        }
        return Resource.Error(message = response.errorBody().toString())
    }



    private fun hasInternetConnection(): Boolean {
        val connectivityManager = app.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return false
    }
}
