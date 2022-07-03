package eg.esperantgada.imagegallery.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import eg.esperantgada.imagegallery.network.ApiService
import eg.esperantgada.imagegallery.paging.SearchPhotoRemoteMediator
import eg.esperantgada.imagegallery.room.ImageDatabase
import eg.esperantgada.imagegallery.room.dao.RemoteKeyDao
import eg.esperantgada.imagegallery.room.dao.SearchImageDao
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
            initialLoadSize = 20,
            pageSize = 20,
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