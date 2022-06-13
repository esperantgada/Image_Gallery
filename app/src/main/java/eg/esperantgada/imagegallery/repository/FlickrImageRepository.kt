package eg.esperantgada.imagegallery.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import eg.esperantgada.imagegallery.network.ApiService
import eg.esperantgada.imagegallery.paging.PhotoPagingSource
import eg.esperantgada.imagegallery.paging.PhotoRemoteMediator
import eg.esperantgada.imagegallery.room.ImageDatabase
import eg.esperantgada.imagegallery.room.dao.ImageDao
import eg.esperantgada.imagegallery.room.dao.RemoteKeyDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlickrImageRepository
@Inject constructor(
    private val apiService: ApiService,
    private val imageDao: ImageDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val imageDatabase: ImageDatabase
    ) {

    //Gets a list of photo the data source and prepare it for the Viewmodel
    @OptIn(ExperimentalPagingApi::class)
    fun getApiPhoto() = Pager(
        config = PagingConfig(
            pageSize = 100,
            maxSize = 300,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { imageDao.getAllPhotos() },
        remoteMediator = PhotoRemoteMediator(apiService, imageDao, remoteKeyDao, imageDatabase)
    ).liveData


}