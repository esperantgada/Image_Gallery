package eg.esperantgada.imagegallery.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import eg.esperantgada.imagegallery.repository.FlickrImageRepository
import eg.esperantgada.imagegallery.repository.SearchImageRepository
import eg.esperantgada.imagegallery.utils.CURRENT_QUERY
import eg.esperantgada.imagegallery.utils.DEFAULT_QUERY
import javax.inject.Inject

@HiltViewModel
class SearchImageViewModel
@Inject constructor(
    searchImageRepository: SearchImageRepository,
    stateHandle: SavedStateHandle
) : ViewModel(){

    private val currentQuery = stateHandle.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    /**
     * This method will be called in the Fragment to get user's Query and set it to the current Query
     */
    fun setQuery(query : String){
        currentQuery.value = query
    }


    /**
     * [switchMap] is called here to switch from one query to a new query that is passed in
     * [getSearchResult] method in the repository in order to update data accordingly
     * Calling [cachedIn] method allows to handle app crashing when rotating device
     */
    val photosList = currentQuery.switchMap { newQuery ->
        searchImageRepository.getPhoto(newQuery).cachedIn(viewModelScope)
    }
}

