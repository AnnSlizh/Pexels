package by.slizh.pexelsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import by.slizh.pexelsapp.data.response.Photo
import by.slizh.pexelsapp.data.response.PhotoList
import by.slizh.pexelsapp.data.response.repository.PhotoRepository
import by.slizh.pexelsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val photoRepository: PhotoRepository) :
    ViewModel() {

    private val _photoList = MutableLiveData<Resource<PhotoList>>()
    val photoList: LiveData<Resource<PhotoList>> = _photoList

    private val _photo = MutableLiveData<Resource<Photo>>()
    val photo: LiveData<Resource<Photo>> = _photo

    fun getCuratedPhotoList() {
        _photoList.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = photoRepository.getCuratedPhotoList()
            _photoList.postValue(photoListResponse(response))
        }
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

//     fun getPhotoById(photoId: Int): LiveData<Photo> {
//        return liveData { photoRepository.getPhotoById(photoId) }
//    }

    fun getPhotoById(photoId: Int) = viewModelScope.launch {
        _photo.postValue(Resource.Loading())
        val response = photoRepository.getPhotoById(photoId)
        _photo.postValue(photoResponse(response))
    }
}
