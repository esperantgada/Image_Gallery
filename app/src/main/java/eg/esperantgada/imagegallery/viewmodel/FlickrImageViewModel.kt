package eg.esperantgada.imagegallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import eg.esperantgada.imagegallery.network.ApiService
import eg.esperantgada.imagegallery.repository.FlickrImageRepository
import eg.esperantgada.imagegallery.room.dao.ImageDao
import javax.inject.Inject

@HiltViewModel
class FlickrImageViewModel
@Inject constructor(
    flickrImageRepository: FlickrImageRepository
) : ViewModel(){

    //Retrieves the list of photos from the repository and prepare them for the UI
    val retrievedPhotos = flickrImageRepository.getApiPhoto().cachedIn(viewModelScope)

}