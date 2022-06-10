package eg.esperantgada.imagegallery.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import eg.esperantgada.imagegallery.network.ApiService
import eg.esperantgada.imagegallery.paging.PhotoPagingSource
import eg.esperantgada.imagegallery.paging.SearchPhotoPagingSource
import eg.esperantgada.imagegallery.room.dao.SearchImageDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchImageRepository
@Inject constructor(
    private val apiService: ApiService
) {

    //Gets a list of photo the data source and prepare it for the Viewmodel
    @OptIn(ExperimentalPagingApi::class)
    fun getPhoto(searchQuery : String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SearchPhotoPagingSource(apiService, searchQuery) },
    ).liveData


}