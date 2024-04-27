package by.slizh.pexelsapp.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.slizh.pexelsapp.data.local.repository.BookmarkRepository
import by.slizh.pexelsapp.data.response.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {

    fun insertPhoto(photo: Photo) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRepository.insertPhoto(photo)
        }
    }

    fun deletePhoto(photo: Photo) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkRepository.deletePhoto(photo)
        }
    }

    fun getPhotoById(id: Int) = bookmarkRepository.getPhotoById(id)

    val allBookmarks = bookmarkRepository.getAllPhotos()

}