package eg.esperantgada.imagegallery.viewmodel

import android.net.NetworkCapabilities.*
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import eg.esperantgada.imagegallery.repository.SearchImageRepository
import eg.esperantgada.imagegallery.room.entities.SearchItem
import javax.inject.Inject

@HiltViewModel
class SearchImageViewModel
@Inject constructor(
    private val searchImageRepository: SearchImageRepository
) : ViewModel(){



    fun getPhotosListBySearch(searchQuery : String) : kotlinx.coroutines.flow.Flow<PagingData<SearchItem>> {

        return searchImageRepository.getPhoto(searchQuery).cachedIn(viewModelScope).asFlow()
    }

}

