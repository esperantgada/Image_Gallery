package eg.esperantgada.imagegallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import eg.esperantgada.imagegallery.repository.FlickrImageRepository
import javax.inject.Inject

@HiltViewModel
class FlickrImageViewModel
@Inject constructor(
    flickrImageRepository: FlickrImageRepository
) : ViewModel(){

    val retrievedPhotos = flickrImageRepository.getApiPhoto().cachedIn(viewModelScope)

}