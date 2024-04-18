package by.slizh.pexelsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.slizh.pexelsapp.data.response.PhotoList
import by.slizh.pexelsapp.data.response.repository.PhotoListRepository
import by.slizh.pexelsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(private val photoListRepository: PhotoListRepository) :
    ViewModel() {

    private val _photoList = MutableLiveData<Resource<PhotoList>>()
    val photoList: LiveData<Resource<PhotoList>> = _photoList

    fun getCuratedPhotoList() {
        _photoList.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = photoListRepository.getCuratedPhotoList()
            _photoList.postValue(photoResponse(response))
        }
    }

    private fun photoResponse(response: Response<PhotoList>): Resource<PhotoList> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return Resource.Success(it)
            }
        }
        return Resource.Error(message = response.errorBody().toString())
    }
}