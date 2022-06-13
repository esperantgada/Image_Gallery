package eg.esperantgada.imagegallery.paging

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import eg.esperantgada.imagegallery.network.ApiService
import eg.esperantgada.imagegallery.room.ImageDatabase
import eg.esperantgada.imagegallery.room.dao.RemoteKeyDao
import eg.esperantgada.imagegallery.room.dao.SearchImageDao
import eg.esperantgada.imagegallery.room.entities.PhotoItem
import eg.esperantgada.imagegallery.room.entities.RemoteKey
import eg.esperantgada.imagegallery.room.entities.SearchItem
import eg.esperantgada.imagegallery.utils.STARTING_INDEX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

const val TAG = "SearchPhotoRemoteMediator"

@OptIn(ExperimentalPagingApi::class)
class SearchPhotoRemoteMediator (
    private val apiService: ApiService,
    private val searchImageDao: SearchImageDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val searchQuery : String,
    private val imageDatabase: ImageDatabase

    ) : RemoteMediator<Int, SearchItem>(){
    @SuppressLint("LongLogTag")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchItem>,
    ): MediatorResult {

        val pagedDataKey = getPageDataKey(loadType, state)

        val page = when(pagedDataKey){
           is MediatorResult.Success ->{
                return pagedDataKey
            }else ->{
                pagedDataKey as Int
            }
        }

        try {
            val result = apiService.searchImage(searchQuery)

            val endOfList = result.photos.photo.isEmpty()

            imageDatabase.withTransaction {
                if (loadType == LoadType.REFRESH){
                    remoteKeyDao.clearAllKeys()
                    searchImageDao.clearAllPhotos()
                }

                val prevKey = if (page == STARTING_INDEX) null else page - 1
                val nextKey = if (endOfList) null else page + 1

                val keys = result.photos.photo.map { itemId ->
                    itemId.id?.let { it -> RemoteKey(it, prevKey, nextKey) }
                }

                result.photos.photo.map {
                    val photoList : List<SearchItem> = listOf(SearchItem(
                        id = it.id.toString(),
                        farm = it.farm,
                        secret = it.secret,
                        server = it.server,
                        title = it.title,
                        url_s = it.url_s
                    ))
                    searchImageDao.insertAllPhotos(photoList)
                    Log.d(TAG, "PHOTOS LIST IN SEARCHPHOTOREMOTEMEDIATOR : $photoList")
                }
                remoteKeyDao.insertRemoteKeys(keys)
            }
            return MediatorResult.Success(endOfList)

        }catch (exception : IOException){
            return MediatorResult.Error(exception)
        }catch (exception : HttpException){
            return MediatorResult.Error(exception)
        }
    }



    private suspend fun getPageDataKey(
        loadType: LoadType,
        state: PagingState<Int, SearchItem>
    ) : Any{

        return when(loadType){

            LoadType.REFRESH ->{
                val remoteKey = getRefreshRemoteKey(state)
                remoteKey?.nextKey?.minus(1) ?: STARTING_INDEX
            }

            LoadType.APPEND ->{
                val remoteKey = getLastRemoteKey(state)
                val nextKey = remoteKey?.nextKey?.minus(1) ?: MediatorResult.Success(true)

                nextKey
            }

            LoadType.PREPEND ->{
                val remoteKey = getFirstRemoteKey(state)
                val prevKey = remoteKey?.prevKey?.minus(1) ?: MediatorResult.Success(false)

                prevKey

            }

        }
    }


    private suspend fun getRefreshRemoteKey(
        state: PagingState<Int, SearchItem>
    ) : RemoteKey?{

        return withContext(Dispatchers.IO){
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { id ->
                    remoteKeyDao.getRemoteKeyById(id)
                }
            }
        }
    }


    private suspend fun getFirstRemoteKey(
        state: PagingState<Int, SearchItem>
    ) : RemoteKey?{

        return withContext(Dispatchers.IO){
            state.pages.firstOrNull(){it.data.isNotEmpty()}
                ?.data?.firstOrNull()
                .let { image ->
                    image?.id?.let { remoteKeyDao.getRemoteKeyById(it) }
                }
        }
    }


    private suspend fun getLastRemoteKey(
        state: PagingState<Int, SearchItem>
    ) : RemoteKey?{

        return withContext(Dispatchers.IO){
            state.pages.lastOrNull{it.data.isNotEmpty()}
                ?.data?.lastOrNull()
                .let { image ->
                    image?.id?.let { remoteKeyDao.getRemoteKeyById(it) }
                }
        }
    }
}
