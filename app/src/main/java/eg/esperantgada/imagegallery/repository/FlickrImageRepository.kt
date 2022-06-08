package eg.esperantgada.imagegallery.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import eg.esperantgada.imagegallery.network.ApiService
import eg.esperantgada.imagegallery.paging.PhotoPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlickrImageRepository @Inject constructor(private val apiService: ApiService) {

    fun getApiPhoto() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PhotoPagingSource(apiService)
        }
    ).liveData

}