package eg.esperantgada.imagegallery.repository

import androidx.paging.*
import eg.esperantgada.imagegallery.network.ApiService
import eg.esperantgada.imagegallery.paging.PhotoPagingSource
import eg.esperantgada.imagegallery.paging.SearchPhotoPagingSource
import eg.esperantgada.imagegallery.paging.SearchPhotoRemoteMediator
import eg.esperantgada.imagegallery.room.ImageDatabase
import eg.esperantgada.imagegallery.room.dao.RemoteKeyDao
import eg.esperantgada.imagegallery.room.dao.SearchImageDao
import eg.esperantgada.imagegallery.room.entities.SearchItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchImageRepository
@Inject constructor(
    private val apiService: ApiService,
    private val searchImageDao: SearchImageDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val imageDatabase: ImageDatabase
) {

    //Gets a list of photo the data source and prepare it for the Viewmodel
    @OptIn(ExperimentalPagingApi::class)
    fun getPhoto(searchQuery : String)  = Pager(
        config = PagingConfig(
            pageSize = 100,
            maxSize = 300,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { searchImageDao.getAllPhotos() },
        remoteMediator = SearchPhotoRemoteMediator(
            apiService,
            searchImageDao,
            remoteKeyDao,
            searchQuery,
            imageDatabase
        )
    ).liveData


}